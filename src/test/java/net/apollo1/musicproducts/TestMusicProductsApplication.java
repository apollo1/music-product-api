package net.apollo1.musicproducts;

import org.springframework.boot.SpringApplication;

public class TestMusicProductsApplication {

	public static void main(String[] args) {
		SpringApplication.from(MusicProductsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
