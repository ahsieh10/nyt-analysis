import { render, screen, waitFor, within } from "@testing-library/react";
import "@testing-library/jest-dom";
import { MemoryRouter } from "react-router-dom";
import App from "../App";
import {
  TEXT_home_page_accessible_name,
  TEXT_search_button_accessible_name,
} from "../pages/home/Home";
import userEvent from "@testing-library/user-event";
import {
  TEXT_input_box_accessible_name,
  TEXT_submit_button_accessible_name,
  TEXT_about_button_accessible_name,
  TEXT_return_home_accessible_name,
} from "../components/Navbar";
import { act } from "react-dom/test-utils";

/* Note: This file performs integration testing; the server must be running. */

let submitButton: HTMLElement;
let inputBox: HTMLElement;
let aboutButton: HTMLElement;
let returnHome: HTMLElement;

beforeEach(() => {
  render(
    <MemoryRouter initialEntries={["/analyze"]}>
      <App />
    </MemoryRouter>
  );
  submitButton = screen.getByRole("submit-button", {
    name: TEXT_submit_button_accessible_name,
  });
  inputBox = screen.getByRole("textbox", {
    name: TEXT_input_box_accessible_name,
  });
  aboutButton = screen.getByRole("about-button", {
    name: TEXT_about_button_accessible_name,
  });
  returnHome = screen.getByRole("return-home", {
    name: TEXT_return_home_accessible_name,
  });
});

test("tests for error message with invalid query", async () => {
  act(() => {
    userEvent.type(inputBox, "skdjfbdjkbjkg");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const results = screen.getByText(
        /Received IOException in sentiment retrieval. Check your inputted keyword. 😢/i
      );
      expect(results).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("tests for valid query", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText, getAllByText } = within(screen.getByRole("main-body"));
      expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
      expect(getAllByText(/KEYWORDS/i).length).toBeGreaterThan(1);
      expect(getByText(/ARTICLES ANALYZED/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("tests for 1 valid query then 1 invalid query", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  act(() => {
    userEvent.type(inputBox, "skdjfbdjkbjkg");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const results = screen.getByText(
        /Received IOException in sentiment retrieval. Check your inputted keyword. 😢/i
      );
      expect(results).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("tests for 1 valid query, 1 invalid query, then 1 valid query", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  act(() => {
    userEvent.type(inputBox, "skdjfbdjkbjkg");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const results = screen.getByText(
        /Received IOException in sentiment retrieval. Check your inputted keyword. 😢/i
      );
      expect(results).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  act(() => {
    userEvent.clear(inputBox); // clears the previous queries from the box
    userEvent.type(inputBox, "china");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("tests for 2 sequential valid queries", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  act(() => {
    userEvent.clear(inputBox); // clears the previous queries from the box
    userEvent.type(inputBox, "trees");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("integration testing: tests for 1 valid query, then click to return to home page", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(
    () => {
      const { getByText } = within(screen.getByRole("main-body"));
      expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
      expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  act(() => {
    userEvent.click(returnHome);
  });

  await waitFor(
    () => {
      let homePage: HTMLElement;
      homePage = screen.getByRole("home-page", {
        name: TEXT_home_page_accessible_name,
      });

      let searchButton: HTMLElement;
      searchButton = screen.getByRole("search-button", {
        name: TEXT_search_button_accessible_name,
      });

      expect(homePage).toBeInTheDocument();
      expect(searchButton).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});

test("tests for about button on analyze page", async () => {
  act(() => {
    userEvent.click(aboutButton);
  });

  await waitFor(
    () => {
      const results = screen.getByText("About Page");
      expect(results).toBeInTheDocument();
    },
    { timeout: 10000 }
  );

  await waitFor(
    () => {
      const results = screen.getByRole("about-body");
      expect(results).toBeInTheDocument();
    },
    { timeout: 10000 }
  );
});
