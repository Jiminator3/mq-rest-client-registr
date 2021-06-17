package ru.jiminator.mqrestclientregistr.boot.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.jiminator.mqrestclientregistr.Client


@Repository
interface EmployeeRepository : JpaRepository<Client, Long>