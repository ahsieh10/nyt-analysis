import { render, screen } from "@testing-library/react";
import React from "react";
import "@testing-library/jest-dom";
import App from "../App";
import {
  TEXT_home_page_accessible_name,
  TEXT_input_box_accessible_name,
  TEXT_search_button_accessible_name,
  TEXT_about_button_accessible_name,
} from "../pages/home/Home";
import { TEXT_sidebar_accessible_name } from "../pages/analyze/Sidebar";

test("renders the home page and analysis page", () => {
  render(<App />);
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

  const aboutButton = screen.getByRole("about-button", {
    name: TEXT_about_button_accessible_name,
  });
    expect(aboutButton).toBeInTheDocument();
});
