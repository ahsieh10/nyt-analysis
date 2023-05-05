import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import App from "../App";
import {
  TEXT_home_page_accessible_name,
  TEXT_input_box_accessible_name,
  TEXT_search_button_accessible_name,
  TEXT_about_button_accessible_name,
} from "../pages/home/Home";
import { MemoryRouter } from "react-router-dom";
import userEvent from "@testing-library/user-event";
import { act } from "react-dom/test-utils";

let aboutButton: HTMLElement;

beforeEach(() => {
  render(
    <MemoryRouter initialEntries={["/"]}>
      <App />
    </MemoryRouter>
  );
});

test("ensures that all objects are displayed, and tests for about button on home page", async () => {

    aboutButton = screen.getByRole("about-btn-home", {
      name: TEXT_about_button_accessible_name,
    });
    const homePage = screen.getByRole("home-page", {
      name: TEXT_home_page_accessible_name,
    });
    expect(homePage).toBeInTheDocument();

    const inputBoxHome = screen.getByRole("home-input-box", {
      name: TEXT_input_box_accessible_name,
    });
    expect(inputBoxHome).toBeInTheDocument();

    const searchButton = screen.getByRole("search-button", {
      name: TEXT_search_button_accessible_name,
    });
    expect(searchButton).toBeInTheDocument();

  act(() => {
    userEvent.click(aboutButton);
  });

    const results = screen.getByText("About Page");
    expect(results).toBeInTheDocument();

    const results2 = screen.getByRole("about-btn-home");
    expect(results2).toBeInTheDocument();
});
