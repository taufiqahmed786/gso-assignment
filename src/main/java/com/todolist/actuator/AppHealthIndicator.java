package com.todolist.actuator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.todolist.model.Item;
import com.todolist.service.ItemService;

@Component
public class AppHealthIndicator implements HealthIndicator {

    @Autowired
    private ItemService itemService;

    @Override
    public Health health() {
        Collection<Item> items = itemService.findAll();

        if (items == null || items.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", items.size()).build();
    }

}
