FROM openliberty:latest

RUN mkdir /config/lib
COPY src/main/liberty /config/
COPY src/main/liberty /config/
COPY ./target/food-picker.war /config/
COPY ./lib /config/lib

EXPOSE 8080
