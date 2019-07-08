print("Opening TestMenu UI")
GAME:DiscordMenuPresence("Test Main Menu")

--menu:PlayMusic("Hope", "Hope.mid")
menu:SuspendWorldInput()

function onUnload()
	print("Closing TestMenu UI")
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
	menu:RunCommand(text)
	menu:SetElementText("Cmd", "")
end

function onKeyed(character, filtered)
	if (filtered) then
		print("Key Typed.. "..character)
	end
end


