
CREATE TABLE projekt1.admin (
                id_admin INTEGER NOT NULL,
                login VARCHAR NOT NULL,
                haslo VARCHAR NOT NULL,
                CONSTRAINT admin_pk PRIMARY KEY (id_admin)
);


CREATE TABLE projekt1.magazyny (
                id_magazynu VARCHAR(10000)NOT NULL,
                nazwa VARCHAR(10000)NOT NULL,
                adres VARCHAR(10000)NOT NULL,
                id_admin INTEGER NOT NULL,
                CONSTRAINT magazyny_pk PRIMARY KEY (id_magazynu)
);


CREATE TABLE projekt1.klienci (
                id_klient INTEGER NOT NULL,
                imie VARCHAR(10000)NOT NULL,
                nazwisko VARCHAR(10000)NOT NULL,
                adres VARCHAR(10000)NOT NULL,
                CONSTRAINT klienci_pk PRIMARY KEY (id_klient)
);


CREATE TABLE projekt1.sprzeda (
                id_sprzed INTEGER NOT NULL,
                id_klient INTEGER NOT NULL,
                id_produktu INTEGER NOT NULL,
                CONSTRAINT sprzeda_pk PRIMARY KEY (id_sprzed, id_klient, id_produktu)
);


CREATE TABLE projekt1.dostawcy (
                nip INTEGER NOT NULL,
                nazwa VARCHAR(10000)NOT NULL,
                adres VARCHAR(10000)NOT NULL,
                CONSTRAINT dostawcy_pk PRIMARY KEY (nip)
);


CREATE TABLE projekt1.dostawy (
                id_dostawy INTEGER NOT NULL,
                id_magazynu VARCHAR(10000)NOT NULL,
                nip INTEGER NOT NULL,
                data DATE NOT NULL,
                CONSTRAINT dostawy_pk PRIMARY KEY (id_dostawy, id_magazynu)
);


CREATE TABLE projekt1.elektronika (
                id_dostawy INTEGER NOT NULL,
                id_magazynu VARCHAR(10000)NOT NULL,
                id_elektronika INTEGER NOT NULL,
                nazwa VARCHAR(10000)NOT NULL,
                ilosc VARCHAR(10000)NOT NULL,
                cena DOUBLE PRECISION NOT NULL,
                CONSTRAINT elektronika_pk PRIMARY KEY (id_dostawy, id_magazynu, id_elektronika)
);


CREATE TABLE projekt1.narzdzia (
                id_narz INTEGER NOT NULL,
                id_dostawy INTEGER NOT NULL,
                id_magazynu VARCHAR(10000)NOT NULL,
                nazwa VARCHAR(10000)NOT NULL,
                ilosc VARCHAR(10000)NOT NULL,
                cena VARCHAR(10000)NOT NULL,
                CONSTRAINT narzdzia_pk PRIMARY KEY (id_narz, id_dostawy, id_magazynu)
);


CREATE TABLE projekt1.chemia (
                id_chemia INTEGER NOT NULL,
                id_dostawy INTEGER NOT NULL,
                id_magazynu VARCHAR(10000)NOT NULL,
                nazwa VARCHAR(10000)NOT NULL,
                ilosc INTEGER NOT NULL,
                cena DOUBLE PRECISION NOT NULL,
                CONSTRAINT chemia_pk PRIMARY KEY (id_chemia, id_dostawy, id_magazynu)
);


ALTER TABLE projekt1.magazyny ADD CONSTRAINT admin_magazyny_fk
FOREIGN KEY (id_admin)
REFERENCES projekt1.admin (id_admin)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.chemia ADD CONSTRAINT magazyny_chemia_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.narzdzia ADD CONSTRAINT magazyny_narzdzia_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.elektronika ADD CONSTRAINT magazyny_elektronika_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.dostawy ADD CONSTRAINT magazyny_dostawy_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.sprzeda ADD CONSTRAINT klienci_sprzeda__fk
FOREIGN KEY (id_klient)
REFERENCES projekt1.klienci (id_klient)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.dostawy ADD CONSTRAINT dostawcy_dostawy_fk
FOREIGN KEY (nip)
REFERENCES projekt1.dostawcy (nip)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.chemia ADD CONSTRAINT dostawy_chemia_fk
FOREIGN KEY (id_dostawy, id_magazynu)
REFERENCES projekt1.dostawy (id_dostawy, id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.narzdzia ADD CONSTRAINT dostawy_narzdzia_fk
FOREIGN KEY (id_dostawy, id_magazynu)
REFERENCES projekt1.dostawy (id_dostawy, id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.elektronika ADD CONSTRAINT dostawy_elektronika_fk
FOREIGN KEY (id_dostawy, id_magazynu)
REFERENCES projekt1.dostawy (id_dostawy, id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;