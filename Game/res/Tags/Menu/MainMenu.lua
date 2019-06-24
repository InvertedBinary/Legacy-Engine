print("Opening Title Menu")
GAME:DiscordMenuPresence("Title Menu")
menu:SuspendWorldInput()

menu:SetFocused("Cmd")

function onUnload()
	print("Closing Title Menu")
	menu:PlayPrevious()
	menu:ResumeWorldInput()
end

function basicContinueGame()
	menu:continueGame()
end

function luaTestHover()
	--print("Button Hovered Over!")
end

function SetVolume(newPos)
	menu:SetVolume(newPos / 108);
end


