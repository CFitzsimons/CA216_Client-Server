@echo off
javac Client/ChatApplet.java
@echo ^<applet code = "ChatApplet.class" width = 400 height =300^> > Client\chat.html
@echo ^<PARAM name="nickname" value="%1"^> >> Client\chat.html
@echo ^<PARAM name="host" value="localhost"^> >> Client\chat.html
@echo ^<PARAM name="port" value=7777^> >> Client\chat.html
@echo ^</applet^> >> Client\chat.html
appletviewer -J"-Djava.security.policy=Client\all.policy" Client\chat.html
@echo off