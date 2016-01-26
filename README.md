# SimpleDAO-Hibernate

[![Build Status](https://travis-ci.org/thiaguten/simple-dao-hibernate.svg)](https://travis-ci.org/thiaguten/simple-dao-hibernate)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/br.com.thiaguten.persistence/simple-dao-hibernate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/br.com.thiaguten.persistence/simple-dao-hibernate)

SimpleDAO Hibernate Service Provider Interface - SPI Implementation.

In test package was implemented a demo example using the SimpleDAO-Hibernate API. A demonstration where transactions are manually controlled using (beginTransaction, commitTransaction, rollbackTransaction, etc) and another using the SpringFramework using (@Transacional).

Requires JDK 1.6 or higher

## Latest release

SimpleDAO-Hibernate bundle is available from [Maven Central](http://search.maven.org/).

To add a dependency using Maven, use the following:

```xml
<dependency>
    <groupId>br.com.thiaguten.persistence</groupId>
    <artifactId>simple-dao-hibernate</artifactId>
    <version>1.0.0</version>
</dependency>
```

To add a dependency using Gradle:

```
dependencies {
    compile 'br.com.thiaguten.persistence:simple-dao-hibernate:1.0.0'
}
```

