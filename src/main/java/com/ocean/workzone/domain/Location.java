package com.ocean.workzone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Region> regions = new HashSet<>();

    @ManyToOne
    private Location parent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "location_user",
               joinColumns = @JoinColumn(name="locations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Location regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Location addRegion(Region region) {
        this.regions.add(region);
        region.setLocation(this);
        return this;
    }

    public Location removeRegion(Region region) {
        this.regions.remove(region);
        region.setLocation(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Location getParent() {
        return parent;
    }

    public Location parent(Location location) {
        this.parent = location;
        return this;
    }

    public void setParent(Location location) {
        this.parent = location;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Location users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Location addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Location removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
