package events

import nebolax.betternotes.notes.AlexNote

class UpdateChoosenDate(val date: String): Event()
class UpdateChoosenTime(val time: String): Event()
class NotificationAdded: Event()
class RequestMessage: Event()
class SetMessage(val message: String): Event()
class ServCheck: Event()

class RemoveNote(val note: AlexNote): Event()
class NavigateToEditor(val note: AlexNote): Event()