package org.romain.demo2.dao;

import org.romain.demo2.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelDao extends JpaRepository<Label, Integer> {


}
