interface Media {
  rank: number;
  subtype: string;
  caption?: string | null;
  credit?: string | null;
  type: string;
  url: string;
  height: number;
  width: number;
  legacy?:
    | {
        xlarge: string;
        xlargewidth: number;
        xlargeheight: number;
      }
    | {};
  subType?: string;
  crop_name?: string;
}

export interface Keyword {
  name: string;
  major: string;
  rank: number;
  value: string;
}

interface HeadLine {
  main: string;
  print_headline: string | null;
  kicker?: any;
  content_kicker?: any;
  same?: any;
  seo?: any;
  sub?: any;
  name?: any;
}

export interface Article {
  _id: string;
  abstract: string;
  web_url: string;
  snippet: string;
  lead_paragraph: string;
  print_section?: string;
  print_page?: string;
  source: string;
  multimedia: Media[];
  pub_date: string;
  document_type: string;
  news_desk?: string;
  section_name?: string;
  subsection_name?: string;
  byline: any;
  type_of_material: string;
  word_count: number;
  uri: string;
  keywords: Keyword[];
  headline: HeadLine;
}

// Target article format

// export interface Article {
//   _id: string; //_id
//   headline: string; // headline.main
//   webUrl: string; // web_url
//   wordCount: string; // word_count
//   abstract: string; // abstract
//   keywords: Keyword[]; // keywords
//   imageUrl: string; // filter multimedia by subType = "thumbnail";
//   pubDate: string; // pub_date
// }

interface APIResponse {
  overallSentiment: string;
  articles: [
    {
      name: string;
      url: string;
      sentiment: string;
    },
    {
      name: string;
      url: string;
      sentiment: string;
    },
    {
      name: string;
      url: string;
      sentiment: string;
    }
  ];
}

export interface WordDatum {
  text: string;
  value: number;
}

export interface Result {
  query: string;
  articles: Article[];
  overallSentiment: string;
  mostBiasedSentences: string[];
  keywords: WordDatum[];
}
