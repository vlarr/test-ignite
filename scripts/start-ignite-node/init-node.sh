docker run -v "$(pwd)/my-conf.xml:/my-conf.xml" -e "OPTION_LIBS=ignite-rest-http,ignite-aws" -e "CONFIG_URI=/my-conf.xml" -p 8080:8080 -p 47500:47500 apacheignite/ignite:2.10.0

