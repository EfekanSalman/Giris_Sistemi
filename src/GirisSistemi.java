import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GirisSistemi {

    static final String KULLANICI_DOSYASI = "kullanicilar.txt";
    static Scanner scanner = new Scanner(System.in);
    static List<Kullanici> kullanicilar = new ArrayList<>();
    static Kullanici aktifKullanici = null;
    static int mevcutId = 0;

    public static void main(String[] args) {
        kullanicilariYukle();
        hosgeldinEkraniGoster();
    }

    static void kullanicilariYukle() {
        try (BufferedReader br = new BufferedReader(new FileReader(KULLANICI_DOSYASI))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] veri = satir.split(",");
                int id = Integer.parseInt(veri[0]);
                String ad = veri[1];
                String soyad = veri[2];
                String kullaniciAdi = veri[3];
                String sifre = veri[4];
                kullanicilar.add(new Kullanici(id, ad, soyad, kullaniciAdi, sifre));
                if (id > mevcutId) {
                    mevcutId = id;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void kullanicilariKaydet() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(KULLANICI_DOSYASI))) {
            for (Kullanici kullanici : kullanicilar) {
                bw.write(kullanici.getId() + "," + kullanici.getAd() + "," + kullanici.getSoyad() + "," + kullanici.getKullaniciAdi() + "," + kullanici.getSifre());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void hosgeldinEkraniGoster() {
        int secim;
        do {
            System.out.println("1) Kayıt Ol");
            System.out.println("2) Giriş Yap");
            System.out.println("3) Çıkış");
            System.out.print("Seçiminiz: ");
            secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1:
                    kayitOl();
                    break;
                case 2:
                    girisYap();
                    break;
                case 3:
                    System.out.println("Programdan çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (secim != 3);
    }

    static void kayitOl() {
        System.out.print("Adınızı giriniz: ");
        String ad = scanner.nextLine();
        System.out.print("Soyadınızı giriniz: ");
        String soyad = scanner.nextLine();
        System.out.print("Kullanıcı adınızı giriniz: ");
        String kullaniciAdi = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        mevcutId++;
        kullanicilar.add(new Kullanici(mevcutId, ad, soyad, kullaniciAdi, sifre));
        kullanicilariKaydet();
        System.out.println("Kayıt başarıyla oluşturuldu.");
    }

    static void girisYap() {
        System.out.print("Kullanıcı adı: ");
        String kullaniciAdi = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();

        for (Kullanici kullanici : kullanicilar) {
            if (kullanici.getKullaniciAdi().equals(kullaniciAdi) && kullanici.getSifre().equals(sifre)) {
                aktifKullanici = kullanici;
                System.out.println("Giriş başarılı! Hoş geldiniz, " + kullanici.getAd() + " " + kullanici.getSoyad() + ".");
                return;
            }
        }
        System.out.println("Kullanıcı adı veya şifre hatalı.");
    }

    static class Kullanici {
        private int id;
        private String ad;
        private String soyad;
        private String kullaniciAdi;
        private String sifre;

        public Kullanici(int id, String ad, String soyad, String kullaniciAdi, String sifre) {
            this.id = id;
            this.ad = ad;
            this.soyad = soyad;
            this.kullaniciAdi = kullaniciAdi;
            this.sifre = sifre;
        }

        public int getId() {
            return id;
        }

        public String getAd() {
            return ad;
        }

        public String getSoyad() {
            return soyad;
        }

        public String getKullaniciAdi() {
            return kullaniciAdi;
        }

        public String getSifre() {
            return sifre;
        }
    }
}
