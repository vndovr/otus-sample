call mvn clean install
docker build . -t vndovr/otus-sample:event --no-cache
docker push vndovr/otus-sample:event

