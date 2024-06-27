FROM openjdk:17-jdk

ARG GITHUB_ACCESS_TOKEN
ARG POSTGRES_URL
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD

ENV MAVEN_VERSION=3.8.6

# Scarica ed installa Maven
RUN apt-get update \
    && apt-get install -y wget \
    && wget https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    && tar -xzvf apache-maven-$MAVEN_VERSION-bin.tar.gz -C /opt \
    && ln -s /opt/apache-maven-$MAVEN_VERSION /opt/maven \
    && ln -s /opt/maven/bin/mvn /usr/bin/mvn \
    && rm apache-maven-$MAVEN_VERSION-bin.tar.gz

# Aggiungi Maven al PATH
ENV MAVEN_HOME=/opt/maven
ENV PATH=${MAVEN_HOME}/bin:${PATH}

RUN mkdir -p /root/.m2 \
    && echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"' > /root/.m2/settings.xml \
    && echo '          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' >> /root/.m2/settings.xml \
    && echo '          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">' >> /root/.m2/settings.xml \
    && echo '  <servers>' >> /root/.m2/settings.xml \
    && echo '    <server>' >> /root/.m2/settings.xml \
    && echo '      <id>github-sirius-emfjson</id>' >> /root/.m2/settings.xml \
    && echo '      <username>${GITHUB_USERNAME}</username>' >> /root/.m2/settings.xml \
    && echo '      <password>${GITHUB_ACCESS_TOKEN}</password>' >> /root/.m2/settings.xml \
    && echo '    </server>' >> /root/.m2/settings.xml \
    && echo '    <server>' >> /root/.m2/settings.xml \
    && echo '      <id>github-emfjson</id>' >> /root/.m2/settings.xml \
    && echo '      <username>${GITHUB_USERNAME}</username>' >> /root/.m2/settings.xml \
    && echo '      <password>${GITHUB_ACCESS_TOKEN}</password>' >> /root/.m2/settings.xml \
    && echo '    </server>' >> /root/.m2/settings.xml \
    && echo '    <server>' >> /root/.m2/settings.xml \
    && echo '      <id>github-papyrus-sirius-uml2</id>' >> /root/.m2/settings.xml \
    && echo '      <username>${GITHUB_USERNAME}</username>' >> /root/.m2/settings.xml \
    && echo '      <password>${GITHUB_ACCESS_TOKEN}</password>' >> /root/.m2/settings.xml \
    && echo '    </server>' >> /root/.m2/settings.xml \
    && echo '    <server>' >> /root/.m2/settings.xml \
    && echo '      <id>papyrus-uml-services</id>' >> /root/.m2/settings.xml \
    && echo '      <username>${GITHUB_USERNAME}</username>' >> /root/.m2/settings.xml \
    && echo '      <password>${GITHUB_ACCESS_TOKEN}</password>' >> /root/.m2/settings.xml \
    && echo '    </server>' >> /root/.m2/settings.xml \
    && echo '    <server>' >> /root/.m2/settings.xml \
    && echo '      <id>github-sirius-components</id>' >> /root/.m2/settings.xml \
    && echo '      <username>${GITHUB_USERNAME}</username>' >> /root/.m2/settings.xml \
    && echo '      <password>${GITHUB_ACCESS_TOKEN}</password>' >> /root/.m2/settings.xml \
    && echo '    </server>' >> /root/.m2/settings.xml \
    && echo '  </servers>' >> /root/.m2/settings.xml \
    && echo '</settings>' >> /root/.m2/settings.xml

# Definisci la directory di lavoro
WORKDIR /app

# Copia il file pom.xml e le dipendenze Maven (se necessario)
COPY pom.xml .

# Esegui il download delle dipendenze Maven (opzionale, ma utile per la cache)
RUN mvn dependency:go-offline

# Compila il modulo papyrus-web-application
RUN mvn -f papyrus-web-application/pom.xml -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

# Compila l'applicazione e genera il file jar
RUN mvn -f papyrus-web-application/pom.xml package -DskipTests

# Copia il file jar dell'applicazione nella directory di lavoro
COPY papyrus-web-application/target/papyrus-web-application.jar .

# Comando per eseguire l'applicazione Java
CMD ["java", "-jar", "papyrus-web-application.jar", \
     "--spring.datasource.url=${POSTGRES_URL}", \
     "--spring.datasource.username=${POSTGRES_USER}", \
     "--spring.datasource.password=${POSTGRES_PASSWORD}", \
     "--spring.liquibase.change-log=classpath:db/changelog/papyrus-web.db.changelog.xml"]
