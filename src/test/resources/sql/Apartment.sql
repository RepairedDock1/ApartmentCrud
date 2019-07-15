-- Table Creation ----------------------------------------------------------------------------------

CREATE TABLE Apartment(
    APT_ID  INT             NOT NULL,
    APT_NM  VARCHAR(255)    NOT NULL,
    UNITS   INT             NOT NULL,
    PRICE   INT             NOT NULL
);


-- Seeded Data -------------------------------------------------------------------------------------

INSERT INTO Apartment (APT_ID, APT_NM, UNITS, PRICE) VALUES (1, 'Post Uptown', 50, 5000000);