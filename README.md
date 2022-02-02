# NotesStudio

The main purpose of this project was to learn android platform at low level

Task which I was performing is to create notifications system based only on Google's Jetpack android kit

Simple requirements to notifications:

- It can be set to notify user at any time
- It will appear at desired time on phone

## Research

There are three ways of doing jobs in background on android - JobScheduler, Services and Alarms

- _JobScheduler_ is created to perform difficult tasks but cannot run more than 15 minutes
- _Service_ sounds good but:
  - Companies like Honor, Xiaomi, Meizu, etc. have their own OS which tries to minimize energy usage and along with this stops backround services (even if they don't consume any energy)
  - Since android 8, users are always notified about jobs running in background (user will have unremovable notification while service is running) so using Services will provide bad UX
- _Alarms_ - don't require running in background but are still blocked by Xiaomi OS and are reset after phone restarts

### Final decision on notifications

I've chosen alarms as they are originally made for notifications. How I've solved problems:

- _Xiomi OS blocking_ - on the first startup I prompt user to allow special permission (runiing in background) in phone settings
- _Alarms reset on phone restart_ - I save all pending alarms in SQLite database and catch phone boot event and then set pending alarms again.

## Product

As demonstration of my work I've created notes application which has following features:

- Main screen where you can observe preview of all notes + create and delete notes
- Note-editing screen where you can:
  - Set / Edit note title
  - Set / Edit note description
  - Set / Edit start and finish of event (each note is kinda event)
- Receive notifications when any event starts or finishes

### Current bugs

- Alarms are reset when application updates

## Interesting files

- [Design](https://drive.google.com/file/d/1YUz7RiB4MFk3ML46-5QRdFiCsVoh0VOa/view?usp=sharing) in adobeXD
- [Presentation](https://docs.google.com/presentation/d/1_UzSkqGW3QdxDhWVeyui-757Lq1cSNjjH_SNr7UjoPE/edit?usp=sharing) in russian
