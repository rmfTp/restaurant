package org.koreait;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecondhandApplication {
/*환경변수
db.password=1234;db.url=localhost:3306/SECONDHAND;db.username=SECONDHAND;ddl.auto=create;file.path=C:/uploads;file.url=/uploads;kakao.apikey=554171db1e052c89471da8f35fb9e6ac;python.base=C:/trend/.venv/Scripts;python.base2=C:/restaurant/.venv/Scripts;python.restaurant=C:/restaurant;python.trend=C:/trend
 */
	public static void main(String[] args) {
		SpringApplication.run(SecondhandApplication.class, args);
	}

}
