call mvn clean install
docker build . -t vndovr/otus-sample:event -t vndovr/otus-sample:order -t vndovr/otus-sample:auth --no-cache
docker push vndovr/otus-sample:auth
docker push vndovr/otus-sample:event
docker push vndovr/otus-sample:order

