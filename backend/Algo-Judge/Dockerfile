FROM openjdk:17-jdk-slim

# Install C++ compiler, Python, sudo, and unshare
RUN apt-get update && apt-get install -y \
    g++ \
    python3 \
    python3-pip \
    sudo \
    util-linux \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Ensure 'nobody' user exists
RUN id -u nobody >/dev/null 2>&1 || adduser --disabled-password --gecos "" nobody

# Allow running commands as 'nobody' without a password
RUN echo "ALL ALL=(nobody) NOPASSWD:ALL" >> /etc/sudoers

# Create and set working directory
WORKDIR /app

# Copy the built JAR from the target folder
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
