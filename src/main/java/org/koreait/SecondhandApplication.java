package org.koreait;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecondhandApplication {
/*환경변수
db.password=1234;db.url=localhost:3307/SECONDHAND;db.username=SECONDHAND;file.url=/uploads;file.path=C:/uploads;python.dir=C:/trend/.venv/Scripts;python.trend=C:/trend
 */
	public static void main(String[] args) {
		SpringApplication.run(SecondhandApplication.class, args);
	}

}
