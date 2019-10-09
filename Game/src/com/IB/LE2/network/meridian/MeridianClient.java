package com.IB.LE2.network.meridian;

import java.util.Arrays;
import java.util.HashMap;

import com.moremeridian.nc.net.client.ClientHandler;
import com.moremeridian.nc.net.structs.SecureConnectionStruct;
import com.moremeridian.nc.net.wire.NetContext;
import com.moremeridian.nc.security.cryptography.Crypto;

import io.netty.channel.ChannelHandlerContext;

public class MeridianClient extends ClientHandler {

	private HashMap<String, Object> flash = new HashMap<>();
	private SecureConnectionStruct ServerSCS = new SecureConnectionStruct();

	public void Flash(String key, Object o) {
		flash.put(key, o);
	}
	
	public Object Flash(String key) {
		if (flash.containsKey(key)) {
			Object obj = flash.get(key);
			flash.remove(key);
			
			return obj;
		}
		
		return false;
	}
	
	@Override
	public void DataReceived(ChannelHandlerContext ctx, byte[] data) {
		byte data_type = data[0];
		byte[] raw_data = Arrays.copyOfRange(data, 1, data.length);

		String msg;
		if (SelfSCS.SymmetricKey() != null) {
			msg = new String(SelfSCS.DecryptAES(raw_data));
		} else {
			msg = new String(raw_data);
		}

		if (data_type == NetContext.BIT_TEXT) {
			System.out.println("SERVER => Client: " + msg);
		}

		if (data_type == NetContext.BIT_USER_TEXT) {
			System.out.println(msg);
		}

		if (data_type == NetContext.BIT_PUB_KEY) {
			ServerSCS.UpdateKeys(raw_data, null);
			Upload(NetContext.BIT_PUB_KEY, SelfSCS.PubKeyStr());
		}

		if (data_type == NetContext.BIT_SYM_KEY) {
			byte[] first_decryption = Crypto.DecryptBytesRaw(SelfSCS.PrivateKey(), raw_data);
			byte[] second_decryption = Crypto.DecryptBytesRaw(ServerSCS.PublicKey(), first_decryption);

			int key_length = 128 / 8; // Length of AES key over 8 bits = > 16 bytes
			SelfSCS.SetSymmetricKey(Arrays.copyOfRange(second_decryption, 0, key_length));
		}

		if (data_type == NetContext.BIT_LOGIN) {
			boolean success = (msg.equals("1")) ? true : false;
			if (!success) {
				Flash("LoginResult", "You were unable to login. Doublecheck you have entered your credentials correctly.");
			}
		}
	}
}
