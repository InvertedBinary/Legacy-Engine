package com.IB.SL.util;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

public class Audio extends Thread {

    private InputStream inputStream = null;

    byte[] buffer = null;
    int bufferSize = 2048;
    int count = 0;
    int index = 0;

    byte[] convertedBuffer;
    int convertedBufferSize;

    private SourceDataLine outputLine = null;

    private float[][][] pcmInfo;

    private int[] pcmIndex;

    private Packet joggPacket = new Packet();
    private Page joggPage = new Page();
    private StreamState joggStreamState = new StreamState();
    private SyncState joggSyncState = new SyncState();

    private DspState jorbisDspState = new DspState();
    private Block jorbisBlock = new Block(jorbisDspState);
    private Comment jorbisComment = new Comment();
    private Info jorbisInfo = new Info();
    
    public static void main(String[] args) {
    	Audio audio = new Audio("/sound/Music/Hope.ogg", true);
    }
    
    public Audio(String path) {
    	this(path, false);
    }

    public Audio(String path, boolean start) {
        inputStream = Audio.class.getResourceAsStream(path);
        if (start)
        	start_audio();
    }
    
    public void start_audio() {
    	start();
    }
    
    public void pause() {
    	this.suspend();
    }
    
    public void play() {
    	this.resume();
    }
    
    public void run() {
        if(inputStream == null) {
            System.err.println("We don't have an input stream and therefor "
                + "cannot continue.");
            return;
        }

        initializeJOrbis();

        if(readHeader()) {
            if(initializeSound()) {
                readBody();
            }
        }

        cleanUp();
    }

    private void initializeJOrbis() {
        joggSyncState.init();
        joggSyncState.buffer(bufferSize);
        buffer = joggSyncState.data;

    }

    private boolean readHeader() {

        boolean needMoreData = true;
        int packet = 1;

        while(needMoreData) {
            try {
                count = inputStream.read(buffer, index, bufferSize);
            } catch(IOException exception) {
                System.err.println("Could not read from the input stream.");
                System.err.println(exception);
            }

            joggSyncState.wrote(count);

            switch(packet) {
                case 1:
                    switch(joggSyncState.pageout(joggPage)) {
                        case -1: {
                            System.err.println("There is a hole in the first "
                                + "packet data.");
                            return false;
                        }

                        case 0: {
                            break;
                        }

                        case 1:
                            joggStreamState.init(joggPage.serialno());
                            joggStreamState.reset();

                            jorbisInfo.init();
                            jorbisComment.init();

                            if(joggStreamState.pagein(joggPage) == -1) {
                                System.err.println("We got an error while "
                                    + "reading the first header page.");
                                return false;
                            }

                            if(joggStreamState.packetout(joggPacket) != 1) {
                                System.err.println("We got an error while "
                                    + "reading the first header packet.");
                                return false;
                            }

                            if(jorbisInfo.synthesis_headerin(jorbisComment, joggPacket) < 0) {
                                System.err.println("We got an error while "
                                    + "interpreting the first packet. "
                                    + "Apparantly, it's not Vorbis data.");
                                return false;
                            }

                            packet++;
                            break;
                    }

                    if(packet == 1) break;

                	case 2:    
                	case 3:
                    switch(joggSyncState.pageout(joggPage)) {
                        case -1:
                            System.err.println("There is a hole in the second or third packet data.");
                            return false;

                        case 0:
                            break;

                        case 1:
                            joggStreamState.pagein(joggPage);

                            switch(joggStreamState.packetout(joggPacket)) {
                                case -1:
                                    System.err.println("There is a hole in the first packet data.");
                                    return false;

                                case 0:
                                    break;

                                case 1:
                                    jorbisInfo.synthesis_headerin(
                                        jorbisComment, joggPacket);

                                    packet++;

                                    if(packet == 4)
                                        needMoreData = false;

                                    break;
                            }

                            break;
                    }

                    break;
            }

            index = joggSyncState.buffer(bufferSize);
            buffer = joggSyncState.data;

            if(count == 0 && needMoreData)
            {
                System.err.println("Not enough header data was supplied.");
                return false;
            }
        }


        return true;
    }

    private boolean initializeSound()
    {

        convertedBufferSize = bufferSize * 2;
        convertedBuffer = new byte[convertedBufferSize];

        jorbisDspState.synthesis_init(jorbisInfo);

        jorbisBlock.init(jorbisDspState);

        int channels = jorbisInfo.channels;
        int rate = jorbisInfo.rate;

        AudioFormat audioFormat = new AudioFormat((float) rate, 16, channels,
            true, false);
        DataLine.Info datalineInfo = new DataLine.Info(SourceDataLine.class,
            audioFormat, AudioSystem.NOT_SPECIFIED);

        if(!AudioSystem.isLineSupported(datalineInfo)) {
            System.err.println("Audio output line is not supported.");
            return false;
        }

        try {
            outputLine = (SourceDataLine) AudioSystem.getLine(datalineInfo);
            outputLine.open(audioFormat);
        } catch(LineUnavailableException exception) {
            System.out.println("The audio output line could not be opened due "
                + "to resource restrictions.");
            System.err.println(exception);
            return false;
        } catch(IllegalStateException exception) {
            System.out.println("The audio output line is already open.");
            System.err.println(exception);
            return false;
        } catch(SecurityException exception) {
            System.out.println("The audio output line could not be opened due "
                + "to security restrictions.");
            System.err.println(exception);
            return false;
        }

        outputLine.start();

        pcmInfo = new float[1][][];
        pcmIndex = new int[jorbisInfo.channels];

        return true;
    }

    private void readBody() {
        boolean needMoreData = true;

        while(needMoreData) {
            switch(joggSyncState.pageout(joggPage)) {
                case -1:
                case  0:
                    break;
                case  1:
                    joggStreamState.pagein(joggPage);

                    if(joggPage.granulepos() == 0) {
                        needMoreData = false;
                        break;
                    }

                    processPackets: while(true) {
                        switch(joggStreamState.packetout(joggPacket)) {
                            case -1:
                            case  0:
                                break processPackets;
                            case  1:
                                decodeCurrentPacket();
                        }
                    }

                    if(joggPage.eos() != 0) needMoreData = false;
            }

            if(needMoreData)
            {
                index = joggSyncState.buffer(bufferSize);
                buffer = joggSyncState.data;

                try {
                    count = inputStream.read(buffer, index, bufferSize);
                } catch(Exception e) {
                    System.err.println(e);
                    return;
                }

                joggSyncState.wrote(count);

                if(count == 0) needMoreData = false;
            }
        }
    }

    private void cleanUp() {
        joggStreamState.clear();
        jorbisBlock.clear();
        jorbisDspState.clear();
        jorbisInfo.clear();
        joggSyncState.clear();

        try {
        	if(inputStream != null) inputStream.close();
        } catch(Exception e) { }
    }

    private void decodeCurrentPacket() {
        int samples;

        if(jorbisBlock.synthesis(joggPacket) == 0) {
            jorbisDspState.synthesis_blockin(jorbisBlock);
        }

        int range;

        while((samples = jorbisDspState.synthesis_pcmout(pcmInfo, pcmIndex)) > 0) {
            if(samples < convertedBufferSize) {
                range = samples;
            }
            else {
                range = convertedBufferSize;
            }

            for(int i = 0; i < jorbisInfo.channels; i++) {
                int sampleIndex = i * 2;

                for(int j = 0; j < range; j++) {
                    int value = (int) (pcmInfo[0][i][pcmIndex[i] + j] * 32767);

                    if(value > 32767) {
                        value = 32767;
                    }
                    if(value < -32768) {
                        value = -32768;
                    }

                    if(value < 0) value = value | 32768;

                    convertedBuffer[sampleIndex] = (byte) (value);
                    convertedBuffer[sampleIndex + 1] = (byte) (value >>> 8);

                    sampleIndex += 2 * (jorbisInfo.channels);
                }
            }

            outputLine.write(convertedBuffer, 0, 2 * jorbisInfo.channels * range);
            jorbisDspState.synthesis_read(range);
        }
    }

	
}
