print("Opening TestMenu UI")
GAME:DiscordMenuPresence("Test Main Menu")

--menu:PlayMusic("Hope", "Hope.mid")

function onUnload()
	print("Closing TestMenu UI")
	menu:PlayPrevious()
end

function basicContinueGame()
	menu:continueGame()
end

function advancedContinueGame()
	menu:continueGame()
end

function luaTestClick()
    print("Button Clicked!")
end

function luaTestHover()
	print("Button Hovered Over!")
end

function luaSliderTest(newPos)
	--print("NEW FPOSITION: "..newPos)
	menu:SetVolume(newPos / 108);
end
