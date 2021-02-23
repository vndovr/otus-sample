call mvn clean install
docker build . -t vndovr/otus-sample:v3 --no-cache
docker push vndovr/otus-sample:v3