# Use the latest Ubuntu image as the base
FROM ubuntu:latest

# Update package lists and install necessary utilities
RUN apt-get update && apt-get install -y curl unzip git zip xauth libxtst-dev libxrender1 libxt6 libxxf86vm-dev

# Install OpenGL and other graphics dependencies.
RUN apt-get update && apt-get install -y \
    libgl1-mesa-dri \
    libglu1-mesa \
    libglfw3 \
    libglfw3-dev \
    mesa-utils # Replacement for libgl1-mesa-glx

# Install SDKMAN!, Java, SBT, and set JAVA_HOME
RUN /bin/bash -c "\
  curl -s \"https://get.sdkman.io\" | bash && \
  source /root/.sdkman/bin/sdkman-init.sh && \
  sdk version && \
  sdk install java 23-open && \
  sdk install sbt && \
  sdk env | grep JAVA_HOME | cut -d '=' -f 2 > /tmp/java_home && \
  export JAVA_HOME=$(cat /tmp/java_home) && \
  echo \"JAVA_HOME=${JAVA_HOME}\"  >> /etc/profile \
  "

ENV JAVA_HOME=/root/.sdkman/candidates/java/23.0.1-open

# Clone the Git repository
RUN git clone https://github.com/schmidtale/Carcassonne.git /app

# Set the working directory
WORKDIR /app

# Make sure sbt has started once
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh && sbt 'about'"

# Set headless to false. However, this might be not enought on its own
ENV _JAVA_OPTIONS=-Djava.awt.headless=false

# Necessary for X11 and OpenGL access (explicitly specifying host networking)
RUN apt-get update && apt-get install -y x11-apps
ENV DISPLAY host.docker.internal:0
# Run the application using sbt
CMD ["/bin/bash", "-c", "source /root/.sdkman/bin/sdkman-init.sh && sbt run"]