package com.efub.cafetimes.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="menu")
public class Menu {
    @Id
    @Column(name="menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column
    @NotNull
    private String menuName;

    @Column
    @NotNull
    private Integer price;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String menuInfo;
}
