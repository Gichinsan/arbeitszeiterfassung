# Arbeitszeiterfassung
Einfache Applikation zum Nachverfolgen seiner eigenen Arbeitszeit. In Zeiten von HomeOffice kann dies einfach nebenbei auf einem kleinen Server installiert werden. Die Applikation hat intern eine kleine Derby Datenbank integriert.

![Main Page](./src/main/resources/images/menu.png)

## Eingabe der Stunden

Über die Auswahl des Tages kann Start, Pause und Ende der Arbeitszeit eingetragen werden.  Die Berechnung der Nettoarbeitszeit erfolgt dann auf Minuten Basis

![Eingabemaske der Arbeitszeit](./src/main/resources/images/workhours.png)

## Berichtsübersicht

Der aktuelle Monat ist beim Öffnen des Tabs direkt Sichtbar, es können die letzten drei Monate als Bericht angezeigt werden.

![Monatliche Übersicht](./src/main/resources/images/report.png)

## Pausenmanagment

Die Applikation hat ein kleine Möglichkeit per Start und Stop die genutzte Pausenzeit in Minuten anzuzeigen.

![Pausenmangement](./src/main/resources/images/pause.png)

## Einfaches Starten der Applikation

Für die developer variante wird der Port 8888 verwendet und kann wie folgt gestartet werden:
java -jar -Dspring.profiles.active=dev arbeitszeiterfassung-1.0-SNAPSHOT.war

Für die Produktive variante wird der Port 9002 verwendet und kann wie folgt gestartet werden:
java -jar -Dspring.profiles.active=prod arbeitszeiterfassung-1.0-SNAPSHOT.war

