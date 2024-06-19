FROM maven:3.9.6-eclipse-temurin-21

# Install necessary tools and dependencies
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    zip \
    xvfb \
    xorg \
    gtk3.0 \
    libcanberra-gtk3-module \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    libglib2.0-0

# Create Downloads directory and set the working directory
RUN mkdir -p ~/Downloads
WORKDIR ~/Downloads

# Define the JDK version and download URL
ENV JDK_VERSION=zulu21.34.19-ca-crac-jdk21.0.3-linux_x64
ENV JDK_URL=https://cdn.azul.com/zulu/bin/${JDK_VERSION}.tar.gz
ENV JAVA_HOME=/usr/lib/jvm/${JDK_VERSION}

# Download and install the JDK
RUN wget ${JDK_URL} -O /tmp/jdk.tar.gz && \
    mkdir -p /usr/lib/jvm && \
    tar -xzf /tmp/jdk.tar.gz -C /usr/lib/jvm && \
    rm /tmp/jdk.tar.gz

# Set environment variables
ENV PATH=${JAVA_HOME}/bin:${PATH}

# Verify installation
RUN java -version

# Set the DISPLAY environment variable for Xvfb
ENV DISPLAY=:99

# Command to be overridden by GitLab CI or when running the container
CMD ["sh", "-c", "while true; do sleep 1000; done"]
