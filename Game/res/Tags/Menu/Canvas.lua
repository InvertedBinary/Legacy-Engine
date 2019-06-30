print("Opening Canvas UI")
GAME:DiscordMenuPresence("Canvas Test Menu")
menu:SuspendWorldInput()

function onUnload()
	print("Closing Canvas UI")
	menu:PlayPrevious()
	menu:ResumeWorldInput()
end

function basicContinueGame()
	menu:continueGame()
end
