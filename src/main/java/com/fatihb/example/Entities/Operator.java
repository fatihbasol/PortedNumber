package com.fatihb.example.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ISLETMECI")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "KODU")
    private String kodu;

    @Column(name = "ADI")
    private String adi;

    @Column(name = "ISLETMECI_TIPI_ID")
    private Long isletmeciTipiId;

    @Column(name = "RN")
    private String rn;

    @Column(name = "ESKI_KODU")
    private String eskiKodu;

    @Column(name = "DURUM_ID")
    private Long durumID;

    @Column(name = "TANITIM_ZAMANI")
    private Timestamp tanitimZamani;

    @Column(name = "TANITAN_KULLANICI_ID")
    private Long tanitanKullaniciId;

    @Column(name = "GUNCELLEME_ZAMANI")
    private Timestamp guncellemeZamani;

    @Column(name = "GUNCELLEYEN_KULLANICI_ID")
    private Long guncelleyenKullaniciId;





}
