call mvn clean install
docker build . -t vndovr/otus-sample:v4 --no-cache
docker push vndovr/otus-sample:v4

