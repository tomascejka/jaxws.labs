# Build
mvn clean package && docker build -t cz.toce.learn.javaee/jaxws.simplest.faulthandler .

# RUN

docker rm -f jaxws.simplest.faulthandler || true && docker run -d -p 8080:8080 -p 4848:4848 --name jaxws.simplest.faulthandler cz.toce.learn.javaee/jaxws.simplest.faulthandler 