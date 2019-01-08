print("Opening TestMenu UI")

function onUnload()
	print("Closing TestMenu UI")
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
	print("NEW POSITION: "..newPos)
end
