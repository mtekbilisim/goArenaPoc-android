
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/mtekbilisim">
    <img src="https://www.cioupdate.com.tr/wp-content/uploads/2020/06/poc.jpg" alt="Logo">
  </a>

<h3 align="center">Turkcell GoArena POC</h3>

  <p align="center">
    Android Native Application /  Navigation Architecture Component
    <br />
    <a href="http://www.mtekbilisim.com"><strong>MTek Bilişim A.Ş. »</strong></a>
    <br />
    <br />
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>İçerik</summary>
  <ol>
    <li>
      <a href="#about-the-project">Proje hakkında</a>
      <ul>
        <li><a href="#built-with">Mimari hakkında</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Başlangıç</a>
      <ul>
        <li><a href="#installation">Kurulum</a></li>
      </ul>
    </li>
    <li><a href="#contributing">Katılım</a></li>
    <li><a href="#license">Lisans</a></li>
    <li><a href="#contact">İletişim</a></li>
  </ol>
</details>

<img width="898" alt="Screen Shot 2021-02-03 at 21 27 28" src="https://user-images.githubusercontent.com/34453671/106792295-02d35100-6667-11eb-97d6-ca519c2a2056.png" alt="Logo">


Aşağıda uygulamayı geliştirirken kullandığımız yapılara dair bilgileri bulabilirsiniz.

### Mimari Hakkında

Bu bölümde oluşturduğumuz kullandığımız ana kütüphaneler, çatı ve 3ncü parti hizmetler hakkında bilgi mevcuttur.

* [Kotlin](https://kotlinlang.org/)
* [Java 8](https://www.oracle.com/tr/java/technologies/javase-jdk8-downloads.html)
* [Koin Dependencies](https://insert-koin.io)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAsOmABhAwEiwAEBR0ZmX9hYu395Djcu7p85bjdTzYDHVx2RC9eUfaxuN2Ez2WbRpt1vYHGRoCg8MQAvD_BwE&gclsrc=aw.ds)
* [Android Jetpack's Navigation component](https://developer.android.com/guide/navigation)
* [Custom View Components](https://developer.android.com/guide/topics/ui/custom-components)
* [Image Crop](https://github.com/igreenwood/SimpleCropView)
* [Glide v4](https://bumptech.github.io/glide/)
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAiAsOmABhAwEiwAEBR0ZsmldRBZG-OZsDLssJfpn-6IX0JO-hNMxeXbAMElP8pkmziUrj-rCBoC3-sQAvD_BwE&gclsrc=aw.ds)
* [Retrofit](https://square.github.io/#android)
* [OkHttp3](https://square.github.io/okhttp/)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Mvvm Pattern](https://developer.android.com/jetpack/guide)


<br />
<p align="center">
  <a href="https://github.com/mtekbilisim">
    <img src="https://user-images.githubusercontent.com/34453671/106795233-e20cfa80-666a-11eb-8cb1-22e9c9c85409.png" alt="Logo">
  </a>
</p>

MVVM Android geliştirme için en iyi seçeneklerden biridir. Google tarafından tam olarak destekleniyor ve teşvik ediliyor.

* ViewModel Model’den gereken dataları alır UI’ya uygular yani ekrana sunar. Observable datayı kullanır. ViewModel’ de MutableLiveData assign eder. 
* LiveData - MutableLiveData sayesinde UI içindeki tüm degişiklikler bu degişkene direk atanır.
* Glide v4 servis haberleşmelerinde dönen fotoğraf url'lerini performanslı şekilde deneyim sunar.
* Koin kendisine verilen görevleri yerine getirmekle beraber hem kotlinin verdiği sade programlamaya katkı sunarak hem geliştirme hemde yönetmeyi kolaylaştırır. Bağımlılığı azaltır.
* Retrofit - OkHttp3  sunucu ile bağlantı kurmaya sağlayan network katmanı araçlarıdır.
* Image Filter ve Crop seçilen ya da yüklenen fotoğraflara efekt, yazı, çizme, emoji, sticker, croplama işlemlerini yapan bir kısmı open source olup tarafımızca ihtiyacımıza göre düzenlenen
  fotoğraf filtreleme aracıdır.
* Custom View Component bir çok komponent'in yapısı yada lisansı gereği kullanamama problemine örnek, ancak ihtiyacımız olma durumunda kendi componentlerimizi oluşturabilir kullanabiliriz.
    * TextInputView (Custom Edittext Component)
    * PhotoFilter
    * Progress
    
* Coroutine mikro iş parçacıklarıdır. Verilen görevleri mini halde senkron ya da asenkron yönetir sonuca ulaşır.

<!-- GETTING STARTED -->
## Başlangıç

Uygulamayı kendi tarafınızda kurabilmek ve çalıştırabilmek için aşağıdaki adımları takip etmeniz gereklidir.

### Gereksinimler

* Java 8 ( Gradle yapılandırma iş süreçleri için gereklidir )
  ```sh
  Android Studio
  ```
   ```sh
  Java JDK
  ```
   ```sh
  Android Studio SDK
  ```
  
  ### Kurulum

1. Repoyu klonlayın
   ```sh
   git clone https://github.com/mtekbilisim/goArenaPoc-android
   ```
 Daha sonra projeyi açarak çalıştırabilirsiniz.
 
 <!-- CONTRIBUTING -->
## Katılım

Katılım projenin bir POC olması sebebi ile **kapatılmıştır**. Ancak projemizi beğendiyseniz paylaşıma açığız. Bu sebeple MIT lisansını tercih ettik.</br>
[![LinkedIn][linkedin-shield]][linkedin-url]
<!-- LICENSE -->
## Lisans

MIT lisansı altında dağıtılmaktadır.</br>
[![MIT License][license-shield]][license-url]


<!-- CONTACT -->
## İletişim

Burakcan Sezgin - [@brkcnszgn](https://www.linkedin.com/brkcnszgn/) - burakcan.sezgin@mtekbilisim.com

Proje linki : [https://github.com/mtekbilisim/goArenaPoc-android)



<!-- ACKNOWLEDGEMENTS -->
## Teşekkürler!
Bu bökümanın hazırlanmasında emeği geçen diğer kütüphaneler : 
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Img Shields](https://shields.io)
* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Pages](https://pages.github.com)
* [Animate.css](https://daneden.github.io/animate.css)
* [Loaders.css](https://connoratherton.com/loaders)
* [Slick Carousel](https://kenwheeler.github.io/slick)
* [Smooth Scroll](https://github.com/cferdinandi/smooth-scroll)
* [Sticky Kit](http://leafo.net/sticky-kit)
* [JVectorMap](http://jvectormap.com)
* [Font Awesome](https://fontawesome.com)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://opensource.org/licenses/MIT
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/brkcnszgn/
[product-screenshot]: images/screenshot.png

-------------
<p align="center">
  <a href="http://www.mtekbilisim.com/">
    <img src="http://www.mtekbilisim.com/img/logo.png" alt="Logo">
  </a>
</p>
