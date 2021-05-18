call mvn clean install
docker build . -t vndovr/otus-sample:event -t vndovr/otus-sample:order -t vndovr/otus-sample:auth -t vndovr/otus-sample:profile -t vndovr/otus-sample:account -t vndovr/otus-sample:bill -t vndovr/otus-sample:email -t vndovr/otus-sample:warehouse -t vndovr/otus-sample:delivery --no-cache
docker push vndovr/otus-sample:auth
docker push vndovr/otus-sample:event
docker push vndovr/otus-sample:order
docker push vndovr/otus-sample:profile
docker push vndovr/otus-sample:account
docker push vndovr/otus-sample:bill
docker push vndovr/otus-sample:email
docker push vndovr/otus-sample:warehouse
docker push vndovr/otus-sample:delivery


