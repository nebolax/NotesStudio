package events

class UpdateChoosenDate(val date: String): Event()
class UpdateChoosenTime(val time: String): Event()
class NotificationAdded: Event()
class RequestMessage: Event()
class SetMessage(val message: String): Event()
class ServCheck: Event()