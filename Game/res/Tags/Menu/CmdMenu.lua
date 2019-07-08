print("Opening Console UI")
GAME:DiscordMenuPresence("Console Menu")
menu:SuspendWorldInput()

menu:SetFocused("Cmd")

function onUnload()
	print("Closing Console UI")
	menu:PlayPrevious()
	menu:ResumeWorldInput()
end

function basicContinueGame()
	menu:continueGame()
end

function advancedContinueGame()
	menu:continueGame()
end

function luaTestClick()
    --print("Button Clicked!")
	print(menu:GetElementText("Cmd"))
end

function luaTestHover()
	--print("Button Hovered Over!")
end

function luaSliderTest(newPos)
	--print("NEW FPOSITION: "..newPos)
	menu:SetVolume(newPos / 108);
end

function onSubmit(text)
	print("Text Submitted.. "..text)
	menu:SetElementText("Cmd", "")
	menu:RunCommand(text)
end

function onKeyed(character, filtered)
	if (filtered) then
		print("Key Typed.. "..character)
	end
end
