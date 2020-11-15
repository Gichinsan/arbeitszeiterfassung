# arbeitszeiterfassung
Arbeitszeiterfassung mit Pausenerfassung per Knopfdruck


java -jar -Dspring.profiles.active=prod arbeitszeiterfassung-1.0-SNAPSHOT.war


<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
       
        <timestamp>${maven.build.timestamp}</timestamp>
        <maven.build.timestamp.format>yyyy-MM-dd HH:mm</maven.build.timestamp.format>
    </properties>