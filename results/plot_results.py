import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path
from matplotlib.ticker import LogFormatterMathtext, ScalarFormatter

CSV_FILE = Path("results/results.csv")
PLOT_DIR = Path("results/plots")

PLOT_DIR.mkdir(parents=True, exist_ok=True)

df = pd.read_csv(CSV_FILE)

# Convert numeric columns
for column in ["n", "time_ms", "steps", "moves", "comparisons"]:
    df[column] = pd.to_numeric(df[column])


# ============================================================
# AXIS FORMATTING
# ============================================================

def format_axes(ax, logarithmic_y=False):

    # X-axis: 10², 10³, 10⁴, 10⁵
    ax.set_xscale("log")
    ax.xaxis.set_major_formatter(LogFormatterMathtext())

    # Y-axis
    if logarithmic_y:
        ax.set_yscale("log")
        ax.yaxis.set_major_formatter(LogFormatterMathtext())
    else:
        formatter = ScalarFormatter(useMathText=True)
        formatter.set_scientific(True)
        formatter.set_powerlimits((0, 0))
        formatter.set_useOffset(False)
        ax.yaxis.set_major_formatter(formatter)

    ax.grid(True, which="both", alpha=0.25)


def save_plot(filename):
    plt.tight_layout()
    plt.savefig(
        PLOT_DIR / filename,
        dpi=300,
        bbox_inches="tight"
    )
    plt.close()


# ============================================================
# W1 — RANDOM ACCESS
# ============================================================

w1 = df[df["workload"] == "W1"]

plt.figure(figsize=(9, 6))

for structure in ["DynamicArray", "MyLinkedList"]:

    data = w1[
        w1["structure"] == structure
    ].sort_values("n")

    plt.plot(
        data["n"],
        data["time_ms"],
        marker="o",
        linewidth=2,
        label=structure
    )

plt.xlabel("Input size (n)")
plt.ylabel("Median time (ms)")
plt.title("W1 — Random Access: Time vs Input Size")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w1_time.png")


# W1 operations
plt.figure(figsize=(10, 6))

for structure in ["DynamicArray", "MyLinkedList"]:

    data = w1[
        w1["structure"] == structure
    ].sort_values("n")

    plt.plot(
        data["n"],
        data["steps"],
        marker="o",
        label=f"{structure} — Steps"
    )

plt.xlabel("Input size (n)")
plt.ylabel("Steps")
plt.title("W1 — Random Access: Steps vs Input Size")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w1_steps.png")


# ============================================================
# W2 — SEARCH
# ============================================================

w2 = df[df["workload"] == "W2"]

plt.figure(figsize=(9, 6))

for structure in ["DynamicArray", "MyLinkedList"]:

    data = w2[
        w2["structure"] == structure
    ].sort_values("n")

    plt.plot(
        data["n"],
        data["time_ms"],
        marker="o",
        linewidth=2,
        label=structure
    )

plt.xlabel("Input size (n)")
plt.ylabel("Median time (ms)")
plt.title("W2 — Search: Time vs Input Size")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w2_time.png")


# W2 operations
plt.figure(figsize=(10, 6))

for structure in ["DynamicArray", "MyLinkedList"]:

    data = w2[
        w2["structure"] == structure
    ].sort_values("n")

    plt.plot(
        data["n"],
        data["steps"],
        marker="o",
        label=f"{structure} — Steps"
    )

    plt.plot(
        data["n"],
        data["comparisons"],
        marker="^",
        label=f"{structure} — Comparisons"
    )

plt.xlabel("Input size (n)")
plt.ylabel("Operation count")
plt.title("W2 — Search: Operation Counts")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w2_operations.png")


# ============================================================
# W3 — INSERT / REMOVE
# ============================================================

w3 = df[df["workload"] == "W3"]

plt.figure(figsize=(10, 6))

for variant in ["head", "middle"]:

    for structure in ["DynamicArray", "MyLinkedList"]:

        data = w3[
            (w3["variant"] == variant) &
            (w3["structure"] == structure)
        ].sort_values("n")

        if not data.empty:

            plt.plot(
                data["n"],
                data["time_ms"],
                marker="o",
                linewidth=2,
                label=f"{structure} — {variant}"
            )

plt.xlabel("Input size (n)")
plt.ylabel("Median time (ms)")
plt.title("W3 — Insert / Remove: Time vs Input Size")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w3_time.png")


# W3 operations
plt.figure(figsize=(11, 7))

for variant in ["head", "middle"]:

    for structure in ["DynamicArray", "MyLinkedList"]:

        data = w3[
            (w3["variant"] == variant) &
            (w3["structure"] == structure)
        ].sort_values("n")

        if not data.empty:

            plt.plot(
                data["n"],
                data["moves"],
                marker="o",
                label=f"{structure} — {variant} — Moves"
            )

plt.xlabel("Input size (n)")
plt.ylabel("Moves")
plt.title("W3 — Insert / Remove: Move Operations")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w3_operations.png")


# ============================================================
# W4 — MIN HEAP
# ============================================================

w4 = df[df["workload"] == "W4"].sort_values("n")

plt.figure(figsize=(9, 6))

plt.plot(
    w4["n"],
    w4["time_ms"],
    marker="o",
    linewidth=2,
    label="MinHeap"
)

plt.xlabel("Input size (n)")
plt.ylabel("Median time (ms)")
plt.title("W4 — Heap Priority Processing: Time vs Input Size")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w4_time.png")


# W4 operations
plt.figure(figsize=(10, 6))

plt.plot(
    w4["n"],
    w4["steps"],
    marker="o",
    label="Steps"
)

plt.plot(
    w4["n"],
    w4["moves"],
    marker="s",
    label="Moves"
)

plt.plot(
    w4["n"],
    w4["comparisons"],
    marker="^",
    label="Comparisons"
)

plt.xlabel("Input size (n)")
plt.ylabel("Operation count")
plt.title("W4 — Heap Priority Processing: Operation Counts")
plt.legend()

format_axes(plt.gca(), logarithmic_y=True)

save_plot("w4_operations.png")


# ============================================================
# DONE
# ============================================================

print()
print("========================================")
print("ALL PLOTS GENERATED SUCCESSFULLY")
print("========================================")
print("Location: results/plots/")
print()