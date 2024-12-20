package org.test.kurz.tbkurz.helper;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressParser {

    /**
     * Utility method to split an address into street and building number
     *
     * @param address String - input address
     * @return List - Street, buildingNumber
     */
    // Suppress warning about complex regular expressions as it cannot really be simplified with the same logic
    @SuppressWarnings("java:S5843")
    public List<String> splitAddress(final String address) {
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("No address value provided.");
        }
        // Initialize street and building number
        String street = "";
        String buildingNumber = "";

        // Removes whitespaces before and after forward slashes (ex. Rybná 716/ 24, Rybná 716 / 24, Rybná 716 /24)
        // Matches with the slash, therefore the whole part is replaced with the forward slash
        String addressWithoutSpacesAroundSlashes = address.replaceAll("\\s/\\s|\\s/|/\\s", "/");

        // Removes occurrences of "Nº" or "NR" (case-insensitive) preceded by a non-letter character,
        // followed by optional digits and spaces, preserving case
        String addressWithoutNr = addressWithoutSpacesAroundSlashes.replaceAll("(?iu)(?<!\\p{L})\\d*\\s*(Nº\\.?|NR\\.?)", "");

        // Removes trailing dot or comma after digits, capturing the digits with a capturing group
        String addressWithoutTrailingDotComma  = addressWithoutNr.replaceAll("(\\d+)(\\.$|,$)", "$1");

        // Removes trailing dash at the end of the string
        String addressWithoutTrailingDash  = addressWithoutTrailingDotComma.replaceAll("-$", "").trim();

        // Check if the address contains "RUE" (case-insensitive)
        if (addressWithoutTrailingDash.toLowerCase().contains("rue")) {
            int rueIndex = addressWithoutTrailingDash.toLowerCase().indexOf("rue");

            // Check if the substring before "RUE" contains only digits
            String beforeRue = addressWithoutTrailingDash.substring(0, rueIndex).trim();
            if (beforeRue.matches("\\d+")) {
                // Extract building number
                buildingNumber = beforeRue;

                // Extract street name
                street = addressWithoutTrailingDash.substring(rueIndex).trim();
            }
        }

        // Check if the address contains "Ul." (case-insensitive)
        Pattern ulPattern = Pattern.compile("(?<=\\s)ul\\.(?=\\s)", Pattern.CASE_INSENSITIVE);
        Matcher ulMatcher = ulPattern.matcher(addressWithoutTrailingDash);

        if (ulMatcher.find()) {
            int ulIndex = addressWithoutTrailingDash.toLowerCase().indexOf("ul.");

            // Extract the substring before "Ul."
            String beforeUl = addressWithoutTrailingDash.substring(0, ulIndex).trim();
            // Check if the substring before "Ul." contains only digits and at most one letter
            if (beforeUl.matches("\\d*[a-zA-Z]?")) {
                // Combine digits and at most one letter as building number
                buildingNumber = beforeUl;

                // Extract street name
                street = addressWithoutTrailingDash.substring(ulIndex).trim();
            }
        }

        // If building number is empty, proceed with regular extraction
        if (buildingNumber.isEmpty()) {

            // Match the building number pattern at the end of the address

            // This pattern matches various formats of street numbers, potentially including letters and slashes.
            // It matches the following formats:
            // - A sequence of digits, optionally followed by a letter, then an optional slash, then more digits, an optional hyphen, and optional more digits.
            // - A sequence of digits followed by a word character (letter or digit) or more word characters.
            // - A sequence of digits followed by optional letters, then a slash, then either more digits followed by optional letters, or just letters.
            // - A sequence of digits followed by optional letters, then an optional slash, then optional more digits followed by optional letters,
            //   then an optional slash, then more digits followed by optional letters.
            // The pattern is anchored to match only at word boundaries at the end of the input string.
            // buildingNumberGroup: Named capturing group for capturing the entire building number.
            String pattern = "\\b(?<buildingNumberGroup>\\d+[a-zA-Z]?/?\\d*-?\\d*|\\d+\\w*|\\d+[a-zA-Z]*?/" +
                    "(?:\\d+[a-zA-Z]*|[a-zA-Z]+/?\\d*[a-zA-Z]*|\\d+[a-zA-Z]*/?\\d*[a-zA-Z]*/?\\d+[a-zA-Z]*))\\b$";

            Matcher matcher = Pattern.compile(pattern).matcher(addressWithoutTrailingDash);

            if (matcher.find()) {
                buildingNumber = matcher.group("buildingNumberGroup"); // Extract building number
                // Remove the building number from the address
                street = addressWithoutTrailingDash.substring(0, matcher.start()).trim();
            } else {
                // If no building number pattern is found, consider the entire address as street
                street = addressWithoutTrailingDash;
            }
        }
        // Trim any extra characters from the end of the street
        street = street.replaceAll("[\\s.,]+$", "");

        // Output the result
        if (street.isEmpty() || buildingNumber.isEmpty()) {
            throw new IllegalStateException("Failed to parse street and building number from address: " + address);
        } else {
            return List.of(street, buildingNumber);
        }
    }
}
