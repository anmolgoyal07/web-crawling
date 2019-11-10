# it a web-crawling application. In this you can crawl for a url upto nth level. 

It defined a post end point (/crwaling?url=#value&depth=#value) which take url & depth as input and it will give you a AcknowledgementToken in return.

With this AcknowledgementToken you check the status of your request from another defined get endpoint (/crawling?token=#value).

Once your request is processed you can get response of your request via another defined endpoint (/crawling/result?token=#value).



