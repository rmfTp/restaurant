package org.koreait.restaurant.repositories;

import org.koreait.restaurant.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, QuerydslPredicateExecutor<Restaurant> {

}
