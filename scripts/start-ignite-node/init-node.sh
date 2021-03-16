docker run -v "$(pwd)/my-conf.xml:/my-conf.xml" -e "OPTION_LIBS=ignite-rest-http,ignite-aws" -e "CONFIG_URI=/my-conf.xml" -p 8080:8080 -p 10800:10800 -p 11211:11211 -p 47100:47100 -p 47500:47500 -p 49112:49112 apacheignite/ignite:2.9.1

