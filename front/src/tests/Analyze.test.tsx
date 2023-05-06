import { render, screen, waitFor, within } from "@testing-library/react";
import React from "react";
import "@testing-library/jest-dom";
import Analyze from "../pages/analyze/Analyze";
import { MemoryRouter } from "react-router-dom";
import App from "../App";
import { TEXT_navbar_accessible_name } from "../pages/analyze/Analyze";
import userEvent from "@testing-library/user-event";
import {
  TEXT_input_box_accessible_name,
  TEXT_submit_button_accessible_name,
  TEXT_about_button_accessible_name,
} from "../components/Navbar";
import { act } from "react-dom/test-utils";
import Results from "../pages/analyze/results/Results";
import { mockSuccessResponse } from "../mocks/exampleResponse";
import { isSuccessDataResult } from "../api/api";

let submitButton: HTMLElement;
let inputBox: HTMLElement;
let aboutButton: HTMLElement;

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
});

test("tests for error message with invalid query", async () => {
  act(() => {
    userEvent.type(inputBox, "skdjfbdjkbjkg");
    userEvent.click(submitButton);
  });

  await waitFor(() => {
    const results = screen.getByText(
      "Received IOException in sentiment retrieval. Check your inputted keyword. 😢"
    );
    expect(results).toBeInTheDocument();
  });
});

test("tests for valid query", async () => {
  act(() => {
    userEvent.type(inputBox, "japan");
    userEvent.click(submitButton);
  });

  await waitFor(() => {
    expect(screen.getAllByText(/OVERALL SENTIMENT/i).length).toBe(1);
    const { getByText, getAllByText } = within(screen.getByRole("main-body"));
    expect(getByText(/OVERALL SENTIMENT/i)).toBeInTheDocument();
    expect(getByText(/NEGATIVE/i)).toBeInTheDocument();
    expect(getByText(/MOST BIASED SENTENCES/i)).toBeInTheDocument();
    expect(
      getByText(
        /The wreck of a Japanese ship that sank in 1942 after it was torpedoed by an American submarine has been found the Australian government said on Saturday./i
      )
    ).toBeInTheDocument();
    expect(getAllByText(/KEYWORDS/i).length).toBeGreaterThan(1);
    expect(getAllByText(/SOUTH CHINA SEA/i).length).toBeGreaterThan(1);
    expect(getByText(/ARTICLES ANALYZED/i)).toBeInTheDocument();
  });
});

test("tests for about button on analyze page", async () => {
  act(() => {
    userEvent.click(aboutButton);
  });

  await waitFor(() => {
    const results = screen.getByText("About Page");
    expect(results).toBeInTheDocument();
  });

  await waitFor(() => {
    const results = screen.getByRole("about-body");
    expect(results).toBeInTheDocument();
  });
});
