package com.abdulkadir.order.config;

import com.abdulkadir.order.model.Package;
import com.abdulkadir.order.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PackageRepository packageRepository;

    @Autowired
    public DataLoader(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        packageRepository.save(new Package("Basic Package", 100.0, "Basic package description", 30, 10));
    }
}