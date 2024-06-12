# Energiequiz

## Verbinden mit dem Raspberry Pi
Benutzernamen: pi

Passwort: pi4j
1. Tastatur und Maus an den Raspberry Pi anschließen
2. IP-Adresse des Raspberry Pi herausfinden mit dem Befehl:
> ifconfig

3. Über SSH verbinden mit dem Befehl:  
>ssh pi@[IP-Adresse]
4. Das Passwort ist pi4j

## Projekt Builden

Der einfachste Weg, das Projekt zu bauen, ist, den Command `mvn clean package` anzuwenden. Dadurch wird das Projekt direkt gebaut, packaged un zipped in den target Ordner gelegt.

### Gitlab Pipeline Support
Das Projekt besitzt auch eine .gitlab-ci.yml Datei, welche mithilfe eines custom Docker-Image das gesamte Projekt baut, packaged und zipped.

Die Pipeline führt sich automatisch nach jedem neuen Commit aus, kann aber auch manuell gestartet werden.
Über Job artifacts "Browse" kann die ZIP Datei (z. Bsp. EnergieQuiz-1.0-SNAPSHOT.zip) zum Download ausgewählt werden.

## Projekt auf den Raspberry Pi laden

Nachdem man nun das gezippte Projekt hat, muss man die Zip-Datei einfach auf den Raspberry Pi übertragen. Zum Beispiel durch scp: 

`scp EnergieQuiz-1.0-SNAPSHOT.zip pi@address:/home/pi/project.zip`

Dann muss man die Datei einfach mit `unzip project.zip` entpacken. Dann `A` eingeben (falls bereits eine Version des Projekts auf dem Raspberry Pi ist.) und schließlich den Raspberry Pi neu starten.

## Einrichten des Raspberry pi

1. Raspberry Pi OS Bookworm x64bit installieren
2. Führen Sie die folgenden Befehle aus:
> sudo apt install java-common libdrm-dev xdotool

> curl -s "https://get.sdkman.io" | bash

> sdk install maven

> mkdir -p ~/Downloads

> cd ~/Downloads

> wget https://cdn.azul.com/zulu/bin/zulu21.34.19-ca-jdk21.0.3-linux_arm64.deb

> sudo dpkg -i zulu21.34.19-ca-jdk21.0.3-linux_arm64.deb

3. Übertragen Sie das Projekt wie im Abschnitt [Projekt erstellen] beschrieben
4. Führen Sie die folgenden Befehle aus, um den Autostart einzurichten:
> cd /home/pi/

>cp EnergieQuiz-1.0-SNAPSHOT/energyquiz.desktop /etc/xdg/.autostart/energyquiz.desktop

## Allgemeine Projektinformationen

### Quizfragen

Die Quizfragen sind in 3 verschiedenen Sprachen vorhanden. Englisch, Deutsch und Französisch. Dafür gibt es [hier](https://gitlab.fhnw.ch/ip12-23vt/energiequiz/energiequiz/-/tree/main/src/main/resources/json?ref_type=heads) 3 JSON Dateien, mit den Endungen _de, _en, _fr. Jede Frage sollte in jede dieser Dateien eingefügt werden, damit die Fragen auf allen Sprachen angezeigt werden. Der Aufbau der Datei is im folgenden Kapitel beschrieben.

### JSON-Struktur

Das Projekt verwendet verschiedene JSON-Dateien, um Fragen und Assets dynamisch anzuzeigen.

Die JSON-Struktur wird [hier](https://gitlab.fhnw.ch/ip12-23vt/energiequiz/energiequiz/-/blob/main/src/main/resources/json/JsonGuide.txt?ref_type=heads) erklärt, oder folgend in den Tabellen.

#### Questions.json
| Field               | Description                                                                                                                                                  |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| type                | Gibt an ob die Frage eine Sortierfrage oder eine Frage mit nur einer korrekten Antwort ist. 0: Standart, 1: Sortierfrage                                      |
| question            | Die Quizfrage (max 130 Zeichen)                                                                                                                             |
| explanationSorting | Erklärung, weshalb die Antworten korrekt sortiert sind (max. 900 Zeichen)                                                                                                      |
| explanation1        | Erklärung, warum Antwort 1 falsch bzw. korrekt ist (max. 900 Zeichen)                                                                                       |
| explanation2        | Erklärung, warum Antwort 2 falsch bzw. korrekt ist (max. 900 Zeichen)                                                                                       |
| explanation3        | Erklärung, warum Antwort 3 falsch bzw. korrekt ist (max. 900 Zeichen)                                                                                       |
| answer1             | Erste Antwortmöglichkeit (max. 130 Zeichen)                                                                                                                 |
| answer2             | Zweite Antwortmöglichkeit (max. 130 Zeichen)                                                                                                                |
| answer3             | Dritte Antwortmöglichkeit (max. 130 Zeichen)                                                                                                                |
| correct             | Gibt an, welche Antwort korrekt ist. Es wird immer die Nummer der Antwort angeben. Also '2' oder '312'.                                                                                                                          |

#### Rooms.json
 
**room:**

| Field      | Description                                                                      | Example |
|------------|----------------------------------------------------------------------------------|---------|
| name       | Names des Raums                                                                  | Schlafzimmer |
| background | Dateiname für die Hintergrund-Textur                                             | schlafzimmer.png |
| assets     | Enthält die Assets, welche Unten beschrieben werden                              | |

**assets:**

| Field    | Description                                            | Example |
|----------|--------------------------------------------------------|---------|
| device   | Gibt an, ob das Asset ein Device ist. (true/false)     | true    |
| filename | Basis-Dateiname für die Asset-Textur                   | pc.png  |
| posX     | Position des Assets im Raum, X-Achse                   | 0       |
| posY     | Position des Assets im Raum, Y-Achse                   | 400     |
| width    | Breite des Assets                                      | 115     |
| height   | Höhe des Assets                                        | 60      |
| energy   | Energieverbrauch in kWh (nur für Devices)              | 20      |


### Assets und Texturen

Assets können in jeglicher Grösse hochgeladen werden. Für jedes interagierbare Objekt, benötigt es 3 verschiedene Grafiken. Eine welche das Gerät aktiv zeigt, eine welche das Gerät ausgeschalten zeigt und eine Grafik für den "wütenden" Zustand. Diese müssen wie oben bereits beschrieben einen Basis-Dateinamen haben, wie zum Beispiel, `pc.png`. Keine der drei Varianten darf aber so heissen.

Sie müssen `pc_on.png`, `pc_off.png` und `pc_angry.png` bennent werden und im Asset-Ordner gespeichert werden. Wenn es sich dabei um kein Device handelt, kann man es auch einfach als `sofa.png` abspeichern.


### Leaderboard

Die Leaderboard-Informationen werden [hier](https://gitlab.fhnw.ch/ip12-23vt/energiequiz/energiequiz/-/blob/main/src/main/resources/csv/score.csv?ref_type=heads) gespeichert.  
Wenn man das Leaderboard neu aufsetzen will, kann man auf dem Raspberry Pi einfach diese Datei löschen. Im Leaderboard wird der Teamname und der Score in Form von der verbleibenden Energie angezeigt.

Die Teamnamen werden auf Basis der [hier](https://gitlab.fhnw.ch/ip12-23vt/energiequiz/energiequiz/-/blob/main/src/main/resources/csv/names.csv?ref_type=heads) geschriebenen Namen erstellt. Es können beliebig viele Namen hinzugefügt oder abgeändert werden.
