/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.web.application;

import java.util.List;
import java.util.Random;

/**
 * Provides a sample domain name which has a good chance to be unique though it is not guaranteed.
 *
 * @author pcdavid
 */
public class SampleDomainNameProvider {
    private final List<String> sampleDomainNames = List.of(//
            "agnesi", //
            "albattani", //
            "allen", //
            "almeida", //
            "antonelli", //
            "archimedes", //
            "ardinghelli", //
            "aryabhata", //
            "austin", //
            "babbage", //
            "banach", //
            "banzai", //
            "bardeen", //
            "bartik", //
            "bassi", //
            "beaver", //
            "bell", //
            "benz", //
            "bhabha", //
            "bhaskara", //
            "black", //
            "blackburn", //
            "blackwell", //
            "bohr", //
            "booth", //
            "borg", //
            "bose", //
            "bouman", //
            "boyd", //
            "brahmagupta", //
            "brattain", //
            "brown", //
            "buck", //
            "burnell", //
            "cannon", //
            "carson", //
            "cartwright", //
            "carver", //
            "cerf", //
            "chandrasekhar", //
            "chaplygin", //
            "chatelet", //
            "chatterjee", //
            "chaum", //
            "chebyshev", //
            "clarke", //
            "cohen", //
            "colden", //
            "cori", //
            "cray", //
            "curran", //
            "curie", //
            "darwin", //
            "davinci", //
            "dewdney", //
            "dhawan", //
            "diffie", //
            "dijkstra", //
            "dirac", //
            "driscoll", //
            "dubinsky", //
            "easley", //
            "edison", //
            "einstein", //
            "elbakyan", //
            "elgamal", //
            "elion", //
            "ellis", //
            "engelbart", //
            "euclid", //
            "euler", //
            "faraday", //
            "feistel", //
            "fermat", //
            "fermi", //
            "feynman", //
            "franklin", //
            "gagarin", //
            "galileo", //
            "galois", //
            "ganguly", //
            "gates", //
            "gauss", //
            "germain", //
            "goldberg", //
            "goldstine", //
            "goldwasser", //
            "golick", //
            "goodall", //
            "gould", //
            "greider", //
            "grothendieck", //
            "haibt", //
            "hamilton", //
            "haslett", //
            "hawking", //
            "hellman", //
            "heisenberg", //
            "hermann", //
            "herschel", //
            "hertz", //
            "heyrovsky", //
            "hodgkin", //
            "hofstadter", //
            "hoover", //
            "hopper", //
            "hugle", //
            "hypatia", //
            "ishizaka", //
            "jackson", //
            "jang", //
            "jemison", //
            "jennings", //
            "jepsen", //
            "johnson", //
            "joliot", //
            "jones", //
            "kalam", //
            "kapitsa", //
            "kare", //
            "keldysh", //
            "keller", //
            "kepler", //
            "khayyam", //
            "khorana", //
            "kilby", //
            "kirch", //
            "knuth", //
            "kowalevski", //
            "lalande", //
            "lamarr", //
            "lamport", //
            "leakey", //
            "leavitt", //
            "lederberg", //
            "lehmann", //
            "lewin", //
            "lichterman", //
            "liskov", //
            "lovelace", //
            "lumiere", //
            "mahavira", //
            "margulis", //
            "matsumoto", //
            "maxwell", //
            "mayer", //
            "mccarthy", //
            "mcclintock", //
            "mclaren", //
            "mclean", //
            "mcnulty", //
            "mendel", //
            "mendeleev", //
            "meitner", //
            "meninsky", //
            "merkle", //
            "mestorf", //
            "mirzakhani", //
            "montalcini", //
            "moore", //
            "morse", //
            "murdock", //
            "moser", //
            "napier", //
            "nash", //
            "neumann", //
            "newton", //
            "nightingale", //
            "nobel", //
            "noether", //
            "northcutt", //
            "noyce", //
            "panini", //
            "pare", //
            "pascal", //
            "pasteur", //
            "payne", //
            "perlman", //
            "pike", //
            "poincare", //
            "poitras", //
            "proskuriakova", //
            "ptolemy", //
            "raman", //
            "ramanujan", //
            "ride", //
            "ritchie", //
            "rhodes", //
            "robinson", //
            "roentgen", //
            "rosalind", //
            "rubin", //
            "saha", //
            "sammet", //
            "sanderson", //
            "satoshi", //
            "shamir", //
            "shannon", //
            "shaw", //
            "shirley", //
            "shockley", //
            "shtern", //
            "sinoussi", //
            "snyder", //
            "solomon", //
            "spence", //
            "stonebraker", //
            "sutherland", //
            "swanson", //
            "swartz", //
            "swirles", //
            "taussig", //
            "tereshkova", //
            "tesla", //
            "tharp", //
            "thompson", //
            "torvalds", //
            "tu", //
            "turing", //
            "varahamihira", //
            "vaughan", //
            "villani", //
            "visvesvaraya", //
            "volhard", //
            "wescoff", //
            "wilbur", //
            "wiles", //
            "williams", //
            "williamson", //
            "wilson", //
            "wing", //
            "wozniak", //
            "wright", //
            "wu", //
            "yalow", //
            "yonath", //
            "zhukovsky" //
    );

    private final Random random = new Random();

    public String getSampleDomainName() {
        int randomIndex = this.random.nextInt(this.sampleDomainNames.size());
        return this.sampleDomainNames.get(randomIndex);
    }
}
