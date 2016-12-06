# SentimentAnalysisUsingNaiveBayesandRuleBayes

This is a big and complex project. This project has implementation of Rule-Based algorithm and Naive Bayes algorithm for sentiment analysis.
The data consists of amazon customer reiviews for camera. The flow of the code is as follows:-

PAUCR.java-(acronym for Product Analysis using customer reviews)- This file has implementation for reading customer reviews from DataNew.txt file. Then rule base algorithm is applied, in which the algorithm first does POS tagging using RitaWordNet library. Then the sentiment is assigned to each word using words with scores in 'good.txt' and 'bad.txt' files. Other than that, 'negation.txt' has negation words which reverse the meaning of the following word. Other than that, the rule base has rules in which it looks within 3 words behind and ahead of each word to mine for 'context'. 
After this, the file Feature is called to extract features from the dataset sentences/reviews.
Then a naive bayes algorithm is called, which has code that first does a POS tagging and score tagging similar to Rule Based algorithm.
The algorithm then keeps count for how many times each word has appeared in a good sentence or a bad sentence or a neutral sentence.
Then probability of each word appearing in good, bad or neutral sentence is calculated and added to find total probability of a sentence being good or bad.

Finally, results are shown using a JPanel Graph.

YOu will need RitaWordnet jar which is provied here.

Just download and configure the files to run and test the project once.
