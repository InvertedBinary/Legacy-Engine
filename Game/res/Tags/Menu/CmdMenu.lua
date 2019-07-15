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

function onSubmit(text)
	print("Text Submitted.. "..text)
	menu:SetElementText("Cmd", "")
	menu:SetElementText("hist", menu:GetElementText("hist").."\n"..text)
	menu:RunCommand(text)
end

function onKeyed(character, filtered)
	if (filtered) then
		--print("Key Typed.. "..character)
	end
end
