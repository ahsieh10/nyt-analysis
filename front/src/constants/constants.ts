export const animationDuration = 0.7;

export enum Sentiment {
  POSITIVE,
  NEGATIVE,
  NEUTRAL,
}

export const getSentimentBackgroundStyle = (sentiment: Sentiment) => {
  switch (sentiment) {
    case Sentiment.POSITIVE:
      return "background-blue";
    case Sentiment.NEGATIVE:
      return "background-red";
    case Sentiment.NEUTRAL:
      return "background-purple";
  }
};

export const getSentimentTextStyle = (sentiment: Sentiment) => {
  switch (sentiment) {
    case Sentiment.POSITIVE:
      return "text-blue";
    case Sentiment.NEGATIVE:
      return "text-red";
    case Sentiment.NEUTRAL:
      return "text-purple";
  }
};

export const getSentimentHighlightBackgroundStyle = (sentiment: Sentiment) => {
  switch (sentiment) {
    case Sentiment.POSITIVE:
      return "background-highlight-blue";
    case Sentiment.NEGATIVE:
      return "background-highlight-red";
    case Sentiment.NEUTRAL:
      return "background-highlight-purple";
  }
};

export const getSentimentBorderStyle = (sentiment: Sentiment) => {
  switch (sentiment) {
    case Sentiment.POSITIVE:
      return "border-blue";
    case Sentiment.NEGATIVE:
      return "border-red";
    case Sentiment.NEUTRAL:
      return "border-purple";
  }
};
