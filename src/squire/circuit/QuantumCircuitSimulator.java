package squire.circuit;

import java.util.ArrayList;
import java.util.Random;

import squire.circuit.gates.CNOTGate;
import squire.circuit.gates.CPGate;
import squire.circuit.gates.CZGate;
import squire.circuit.gates.HGate;
import squire.circuit.gates.IGate;
import squire.circuit.gates.PGate;
import squire.circuit.gates.RXGate;
import squire.circuit.gates.RYGate;
import squire.circuit.gates.RZGate;
import squire.circuit.gates.SGate;
import squire.circuit.gates.SwapGate;
import squire.circuit.gates.TGate;
import squire.circuit.gates.XGate;
import squire.circuit.gates.YGate;
import squire.circuit.gates.ZGate;

/**
 * {@code QuantumCircuitSimulator} represents a quantum circuit simulator
 * capable of simulating the evolution of a quantum circuit and measuring its
 * final state.
 * 
 * The class allows users to construct quantum circuits by adding various
 * quantum gates and perform simulations to obtain measurement outcomes.
 *
 * {@code QuantumCircuitSimulator} utilizes a {@link StateVector} to represent
 * the quantum state of the system and applies {@link QuantumGate} and
 * {@link Measurement} operations to simulate the evolution of the quantum
 * circuit. The class supports optimization techniques to improve simulation
 * efficiency by combining adjacent gates when possible.
 *
 * @see CircuitModifier
 * @see QuantumGate
 * @see Measurement
 * @see StateVector
 * @see CombinableCircuitModifier
 * @see UncombinableCircuitModifier
 */
public class QuantumCircuitSimulator implements UncombinableCircuitModifier {

	/**
	 * List of quantum gates representing the sequence of operations in the quantum
	 * circuit. Each element is a {@link CircuitModifier}, which can be a quantum
	 * gate or measurement operation.
	 *
	 * @see CircuitModifier
	 * @see CombinableCircuitModifier
	 * @see UncombinableCircuitModifier
	 * @see QuantumGate
	 * @see Measurement
	 */
	private ArrayList<CircuitModifier> gates;
	/**
	 * The number of qubits in the quantum circuit.
	 *
	 * @see QuantumCircuitSimulator
	 */
	private int numQubits;
	/**
	 * Random object used for generating random values during the quantum circuit
	 * simulation.
	 *
	 * @see QuantumCircuitSimulator
	 * @see Random
	 */
	private Random random;

	/**
	 * Initializes a Quantum Circuit Simulator with the specified number of qubits
	 * and a Random object for generating random values.
	 *
	 * The constructor initializes the Quantum Circuit Simulator with the given
	 * number of qubits and a list of quantum gates. It starts with an Identity (I)
	 * gate applied to the first qubit (index 0) to ensure a valid initial state.
	 * The Random object is used for generating random values during the simulation,
	 * facilitating operations like measurements.
	 *
	 * The initial Identity (I) gate ensures that the quantum circuit starts in a
	 * well-defined state before any additional gates are applied.
	 *
	 * @param n      The number of qubits in the quantum circuit.
	 * @param random The Random object used for generating random values during the
	 *               simulation.
	 *
	 * @see QuantumCircuitSimulator
	 * @see QuantumGate
	 * @see IGate
	 * @see Random
	 */
	public QuantumCircuitSimulator(int n, Random random) {
		this.numQubits = n;
		this.gates = new ArrayList<>();
		this.gates.add(new IGate(this.numQubits, 0));
		this.random = random;
	}

	/**
	 * Initializes a quantum circuit with n qubits to the zero-state.
	 * 
	 * @param n The number of qubits.
	 */
	public QuantumCircuitSimulator(int n) {
		this.numQubits = n;
		this.gates = new ArrayList<>();
		this.gates.add(new IGate(this.numQubits, 0));
		this.random = new Random();
	}

	/**
	 * Adds a Hadamard (H) gate operation to the quantum circuit, creating a
	 * superposition of states on the specified qubit.
	 *
	 *
	 * The method creates a Hadamard (H) gate operation with the given qubit index
	 * and adds it to the quantum circuit. The Hadamard gate is a single-qubit gate
	 * that creates a superposition of states on the targeted qubit.
	 *
	 * The Hadamard gate is added to the circuit, and its application will modify
	 * the state vector during the quantum circuit execution, introducing a
	 * superposition of states to the targeted qubit.
	 *
	 * @param q The index of the qubit to which the Hadamard gate is applied.
	 * 
	 * @see HGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void h(int q) {
		QuantumGate g = new HGate(this.numQubits, q);
		this.addGate(g);
	}

	/**
	 * Adds a Controlled-X (CNOT) gate operation to the quantum circuit, applying a
	 * bit flip to the target qubit based on the state of the control qubit.
	 *
	 * The method creates a Controlled-X (CNOT) gate operation with the given qubit
	 * indices and adds it to the quantum circuit. The CNOT gate performs a bit flip
	 * on the target qubit if and only if the state of the control qubit is |1>.
	 *
	 * The CNOT gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing conditional
	 * bit flips based on the control qubit state.
	 *
	 * @param q The index of the target qubit on which the CNOT gate is applied.
	 * @param c The index of the control qubit that influences the CNOT gate
	 *          operation.
	 *
	 * @see CNOTGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void cx(int q, int c) {
		QuantumGate g = (new CNOTGate(this.numQubits, q, c));
		this.addGate(g);
	}

	/**
	 * Adds a Pauli-X (X) gate operation to the quantum circuit, performing a bit
	 * flip on the specified qubit.
	 *
	 * The method creates a Pauli-X (X) gate operation with the given qubit index
	 * and adds it to the quantum circuit. The Pauli-X gate is a single-qubit gate
	 * that performs a bit flip on the state of the targeted qubit.
	 *
	 * The Pauli-X gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing a bit flip to
	 * the targeted qubit.
	 *
	 * @param q The index of the qubit to which the Pauli-X gate is applied.
	 *
	 * @see XGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void x(int q) {
		QuantumGate g = (new XGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds a Pauli-Y (Y) gate operation to the quantum circuit on the specified
	 * qubit.
	 *
	 * The method creates a Pauli-Y (Y) gate operation with the given qubit index
	 * and adds it to the quantum circuit.
	 *
	 * @param q The index of the qubit to which the Pauli-Y gate is applied.
	 * 
	 * @see YGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void y(int q) {
		QuantumGate g = (new YGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds a Pauli-Z (Z) gate operation to the quantum circuit, performing a π
	 * phase flip on the specified qubit.
	 *
	 * The method creates a Pauli-Z (Z) gate operation with the given qubit index
	 * and adds it to the quantum circuit. The Pauli-Z gate is a single-qubit gate
	 * that performs a π phase flip on the state of the targeted qubit.
	 *
	 * The Pauli-Z gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing a π phase flip
	 * to the targeted qubit.
	 *
	 * @param q The index of the qubit to which the Pauli-Z gate is applied.
	 *
	 * @see ZGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void z(int q) {
		QuantumGate g = (new ZGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds a phase (P) gate operation to the quantum circuit, performing a phase
	 * angle of theta on the specified qubit.
	 *
	 * The method creates a phase (P) gate operation with the given qubit index and
	 * phase angle and adds it to the quantum circuit. The phase gate is a
	 * single-qubit gate that performs a phase shift on the state of the targeted
	 * qubit.
	 *
	 * The phase gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing a phase shift
	 * of theta to the targeted qubit.
	 *
	 * @param theta The phase shift to apply to the qubit
	 * @param q     The index of the qubit to which the phase gate is applied.
	 *
	 * @see PGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void p(double theta, int q) {
		QuantumGate g = (new PGate(this.numQubits, q, theta));
		this.addGate(g);
	}

	/**
	 * Adds an Identity (I) gate operation to the quantum circuit, performing no
	 * operation on the specified qubit.
	 *
	 * The method creates an Identity (I) gate operation with the given qubit index
	 * and adds it to the quantum circuit. The Identity gate is a single-qubit gate
	 * that performs no operation, leaving the state of the targeted qubit
	 * unchanged.
	 *
	 * The Identity gate is added to the circuit, and its application will have no
	 * effect on the state vector during the quantum circuit execution for the
	 * targeted qubit.
	 * 
	 * @param q The index of the qubit to which the Identity gate is applied.
	 *
	 * @see IGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void i(int q) {
		QuantumGate g = (new IGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds an Rx gate operation to the quantum circuit, rotating the specified
	 * qubit around the X-axis by a given angle.
	 *
	 * The method creates an Rx gate operation with the given qubit index and
	 * rotation angle and adds it to the quantum circuit. The Rx gate is a
	 * single-qubit gate that performs a rotation around the X-axis by the specified
	 * angle.
	 *
	 * The Rx gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing the specified
	 * X-axis rotation to the targeted qubit.
	 *
	 * @param q     The index of the qubit to be rotated.
	 * @param theta The angle (in radians) by which the qubit is rotated around the
	 *              Z-axis.
	 *
	 * @see RXGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void rx(int q, double theta) {
		QuantumGate g = (new RXGate(this.numQubits, q, theta));
		this.addGate(g);
	}

	/**
	 * Adds an Ry gate operation to the quantum circuit, rotating the specified
	 * qubit around the Y-axis by a given angle.
	 *
	 * The method creates an Ry gate operation with the given qubit index and
	 * rotation angle and adds it to the quantum circuit. The Ry gate is a
	 * single-qubit gate that performs a rotation around the Y-axis by the specified
	 * angle.
	 *
	 * The Ry gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing the specified
	 * Y-axis rotation to the targeted qubit.
	 *
	 * @param q     The index of the qubit to be rotated.
	 * @param theta The angle (in radians) by which the qubit is rotated around the
	 *              Z-axis.
	 *
	 * @see RYGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void ry(int q, double theta) {
		QuantumGate g = (new RYGate(this.numQubits, q, theta));
		this.addGate(g);
	}

	/**
	 * Adds an Rz gate operation to the quantum circuit, rotating the specified
	 * qubit around the Z-axis by a given angle.
	 *
	 * The method creates an Rz gate operation with the given qubit index and
	 * rotation angle and adds it to the quantum circuit. The Rz gate is a
	 * single-qubit gate that performs a rotation around the Z-axis by the specified
	 * angle.
	 *
	 * The Rz gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing the specified
	 * Z-axis rotation to the targeted qubit.
	 *
	 * @param q     The index of the qubit to be rotated.
	 * @param theta The angle (in radians) by which the qubit is rotated around the
	 *              Z-axis.
	 * @see RZGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void rz(int q, double theta) {
		QuantumGate g = (new RZGate(this.numQubits, q, theta));
		this.addGate(g);
	}

	/**
	 * Adds an S gate operation to the quantum circuit, applying a π/2 phase shift
	 * to the specified qubit.
	 *
	 * The method creates an S gate operation with the given qubit index and adds it
	 * to the quantum circuit. The S gate is a single-qubit gate that introduces a
	 * π/2 phase shift (square root of Z gate) to the state of the specified qubit.
	 *
	 * The S gate is added to the circuit, and its application will modify the state
	 * vector during the quantum circuit execution, introducing the π/2 phase shift
	 * to the targeted qubit.
	 *
	 * @param q The index of the qubit on which the S gate is applied.
	 *
	 * @see SGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void s(int q) {
		QuantumGate g = (new SGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds a T gate operation to the quantum circuit, applying a π/4 phase shift to
	 * the specified qubit.
	 * 
	 * The method creates a T gate operation with the given qubit index and adds it
	 * to the quantum circuit. The T gate is a single-qubit gate that introduces a
	 * π/4 phase shift to the state of the specified qubit.
	 *
	 * The T gate is added to the circuit, and its application will modify the state
	 * vector during the quantum circuit execution, introducing the π/4 phase shift
	 * to the targeted qubit.
	 *
	 * @param q The index of the qubit on which the T gate is applied.
	 *
	 *
	 * @see TGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void t(int q) {
		QuantumGate g = (new TGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Adds a Controlled-Z (CZ) gate operation to the quantum circuit.
	 * 
	 * The method creates a Controlled-Z (CZ) gate operation with the given qubit
	 * indices and adds it to the quantum circuit. The CZ gate introduces a phase
	 * shift in the target qubit based on the state of the control qubit.
	 *
	 * The CZ gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing conditional
	 * phase shifts based on the control qubit state.
	 *
	 * @param q The index of the target qubit on which the CZ gate is applied.
	 * @param c The index of the control qubit that influences the CZ gate
	 *          operation.
	 *
	 * @see CZGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void cz(int q, int c) {
		QuantumGate g = (new CZGate(this.numQubits, q, c));
		this.addGate(g);
	}

	/**
	 * Adds a Controlled-Phase (CP) gate operation to the quantum circuit.
	 * 
	 * The method creates a Controlled-Phase (CP) gate operation with the given
	 * qubit indices and phase shift and adds it to the quantum circuit. The CP gate
	 * introduces a phase shift in the target qubit based on the state of the
	 * control qubit.
	 *
	 * The CP gate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, introducing conditional
	 * phase shifts based on the control qubit state.
	 *
	 * @param theta The number of radians to shift the relative phase.
	 * @param q     The index of the target qubit on which the CZ gate is applied.
	 * @param c     The index of the control qubit that influences the CZ gate
	 *              operation.
	 *
	 * @see CPGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void cp(double theta, int q, int c) {
		QuantumGate g = (new CPGate(this.numQubits, q, c, theta));
		this.addGate(g);
	}

	/**
	 * Adds a SwapGate operation to the quantum circuit, swapping the states of two
	 * specified qubits.
	 *
	 * The method creates a SwapGate operation with the given qubit indices and adds
	 * it to the quantum circuit. SwapGate is a quantum gate that exchanges the
	 * quantum states of two qubits.
	 *
	 * The SwapGate is added to the circuit, and its application will modify the
	 * state vector during the quantum circuit execution, reflecting the swapping of
	 * states between the specified qubits.
	 *
	 * @param q The index of the first qubit to be swapped.
	 * @param c The index of the second qubit to be swapped.
	 *
	 * @see SwapGate
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void swap(int q, int c) {
		QuantumGate g = (new SwapGate(this.numQubits, q, c));
		this.addGate(g);
	}

	/**
	 * Adds a measurement operation to the quantum circuit for the specified qubit.
	 *
	 * The method adds a Measurement operation to the quantum circuit for the
	 * specified qubit. Measurement operations are essential for extracting
	 * classical information from a quantum system. The provided Random object is
	 * used for generating random outcomes during the measurement process.
	 *
	 * The Measurement operation is added to the circuit, and it will affect the
	 * subsequent evolution of the state vector during circuit execution.
	 * Measurements collapse the quantum state, providing classical outcomes for the
	 * measured qubits.
	 *
	 * @param q The index of the qubit to be measured.
	 * @param r The Random object used for generating measurement outcomes.
	 *
	 * @see Measurement
	 * @see QuantumCircuitSimulator
	 * @see StateVector
	 */
	public void measure(int q, Random r) {

		this.addGate(new Measurement(this.numQubits, q, r));
	}

	/**
	 * Applies a measurement to the specified qubit, causing a collapse of the
	 * state. Uses this circuit's saved random source.
	 * 
	 * @param q The qubit to measure.
	 */
	public void measure(int q) {
		this.gates.add(new Measurement(this.numQubits, q, this.random));
	}

	/**
	 * Executes the quantum circuit, simulating its evolution and measuring the
	 * final state.
	 *
	 * The method initializes a StateVector with a zero state for the specified
	 * number of qubits. It then adds measurement operations to the circuit for each
	 * qubit, runs the quantum circuit by applying its gates, and finally measures
	 * the resulting state vector.
	 *
	 * The StateVector is manipulated by applying CircuitModifier instances, which
	 * represent quantum gates and measurement operations. The simulation proceeds
	 * by applying each gate in the order they were added to the circuit.
	 *
	 * The measurement outcomes are obtained by applying measurement operations to
	 * the final state vector. The boolean array returned corresponds to the
	 * measured outcomes of each qubit in the circuit.
	 *
	 * @return An array of boolean values representing the measured outcomes of the
	 *         qubits.
	 *
	 * @see StateVector
	 * @see CircuitModifier
	 * @see Measurement
	 * @see QuantumGate
	 */
	public boolean[] run() {
		return this.run(false);
	}

	/**
	 * Executes the quantum circuit, simulating its evolution and measuring the
	 * final state.
	 *
	 * The method initializes a StateVector with a zero state for the specified
	 * number of qubits. It then adds measurement operations to the circuit for each
	 * qubit, runs the quantum circuit by applying its gates, and finally measures
	 * the resulting state vector.
	 *
	 * The StateVector is manipulated by applying CircuitModifier instances, which
	 * represent quantum gates and measurement operations. The simulation proceeds
	 * by applying each gate in the order they were added to the circuit. If the
	 * debug parameter is true, the intermediate state vector is printed for
	 * debugging purposes.
	 *
	 * The measurement outcomes are obtained by applying measurement operations to
	 * the final state vector. The boolean array returned corresponds to the
	 * measured outcomes of each qubit in the circuit.
	 *
	 * @param debug If true, prints the state vector after applying all gates for
	 *              debugging purposes.
	 *
	 * @return The boolean array representing the measured outcomes of the qubits
	 *         after the circuit execution.
	 *
	 * @see StateVector
	 * @see CircuitModifier
	 * @see Measurement
	 * @see QuantumGate
	 */
	public boolean[] run(boolean debug) {

		StateVector sv = StateVector.createZeroState(this.numQubits);

		ArrayList<CircuitModifier> measurements = new ArrayList<>();
		// Add measurements.
		for (int i = 0; i < this.numQubits; i++) {
			measurements.add(new Measurement(this.numQubits, i, this.random));
		}

		// Run the circuit.
		for (CircuitModifier cm : this.gates) {
			sv = cm.apply(sv);
		}

		if (debug) {
			System.out.println(sv);
		}

		// Measure.
		for (CircuitModifier cm : measurements) {
			sv = cm.apply(sv);
		}

		boolean[] vals = new boolean[this.numQubits];

		// Take measurements.
		for (int i = 0; i < this.numQubits; i++) {
			vals[i] = sv.getMeasurement(i);
		}

		return vals;
	}

	/**
	 * Adds a QuantumGate to the quantum circuit, optimizing the circuit structure
	 * for better efficiency. The method checks if the provided gate is an instance
	 * of UncombinableCircuitModifier, and if so, attempts to simplify the circuit
	 * by combining adjacent CombinableCircuitModifier gates in pairs. After the
	 * simplification process, the provided gate is added to the circuit.
	 * 
	 * The method optimizes the circuit by iteratively combining pairs of adjacent
	 * gates while preserving the order of UncombinableCircuitModifier gates. If the
	 * provided gate is an instance of UncombinableCircuitModifier, the circuit is
	 * simplified before adding the gate. The simplification process involves
	 * merging adjacent CombinableCircuitModifier gates until no further
	 * combinations are possible.
	 *
	 * The optimization process ensures that the circuit remains valid, and the
	 * gates are combined based on the rules defined by the
	 * CombinableCircuitModifier interface.
	 *
	 * The method has a time complexity of approximately O(lg r), where r is the
	 * number of CombinableCircuitModifier gates in a row. However, the overall
	 * complexity is influenced by the structure of the circuit and the type of
	 * gates present.
	 *
	 * @param g The QuantumGate to be added to the circuit.
	 *
	 *
	 * @see QuantumGate
	 * @see UncombinableCircuitModifier
	 * @see CombinableCircuitModifier
	 * @see QuantumGate#combine(QuantumGate)
	 */
	private void addGateFast(CircuitModifier g) {

		if (g.numQubits() > this.numQubits) {
			throw new IllegalArgumentException("Cannot apply an operator that takes " + g.numQubits()
					+ "qubits to a circuit with " + this.numQubits + " qubits");
		}

		// Check if you're adding an uncombinable gate.
		if (g instanceof UncombinableCircuitModifier) {
			// If it is uncombinable, simplify the circuit.
			int i;
			i = this.gates.size() - 1;

			// While the top two gates can be combined, simplify the circuit in pairs.
			while (i >= 1 && this.gates.get(i) instanceof CombinableCircuitModifier
					&& this.gates.get(i - 1) instanceof CombinableCircuitModifier) {
				// Loop combining each pair until you hit the end.
				while (i >= 1 && this.gates.get(i) instanceof CombinableCircuitModifier gate1
						&& this.gates.get(i - 1) instanceof CombinableCircuitModifier gate2) {
					// Simplify this pair, then move i beneath it.
					this.gates.remove(i);
					this.gates.remove(i - 1);
					this.gates.add(gate1.combine(gate2));
					i -= 2;
				}
				// Reset i to the end of the circuit.
				i = this.gates.size() - 1;
			}
		}

		// Now add the gate.
		this.gates.add(g);
	}

	/**
	 * Adds a QuantumGate to the quantum circuit, optimizing the circuit structure
	 * for better efficiency. The method checks if the provided gate is an instance
	 * of UncombinableCircuitModifier, and if so, attempts to simplify the circuit
	 * by combining adjacent CombinableCircuitModifier gates in pairs. After the
	 * simplification process, the provided gate is added to the circuit.
	 *
	 * The method optimizes the circuit by iteratively combining pairs of adjacent
	 * gates while preserving the order of UncombinableCircuitModifier gates. If the
	 * provided gate is an instance of UncombinableCircuitModifier, the circuit is
	 * simplified before adding the gate. The simplification process involves
	 * merging adjacent CombinableCircuitModifier gates until no further
	 * combinations are possible.
	 *
	 * The optimization process ensures that the circuit remains valid, and the
	 * gates are combined based on the rules defined by the
	 * CombinableCircuitModifier interface.
	 *
	 * The method has a time complexity of approximately O(lg r), where r is the
	 * number of CombinableCircuitModifier gates in a row. However, the overall
	 * complexity is influenced by the structure of the circuit and the type of
	 * gates present.
	 *
	 * @param g The QuantumGate to be added to the circuit.
	 *
	 * @see QuantumGate
	 * @see UncombinableCircuitModifier
	 * @see CombinableCircuitModifier
	 * @see QuantumGate#combine(QuantumGate)
	 */
	public QuantumCircuitSimulator addGate(CircuitModifier g) {
		this.addGateFast(g);
		return this;
	}

	@Override
	public StateVector apply(StateVector state) {
		// Run the circuit.
		for (CircuitModifier cm : this.gates) {
			state = cm.apply(state);
		}

		return state;
	}

	/**
	 * Returns the number of qubits in the quantum circuit.
	 *
	 * The method retrieves the number of qubits stored in the circuit. Qubits are
	 * fundamental units of quantum information, and their count indicates the size
	 * or capacity of the quantum circuit.
	 *
	 * This function does not modify the circuit in any way.
	 *
	 * The method has a constant time complexity, as it directly returns the
	 * precomputed number of qubits stored in the circuit without iterating or
	 * performing any additional calculations.
	 *
	 * @return The integer representing the total number of qubits in the quantum
	 *         circuit.
	 *
	 * @see QuantumCircuitSimulator
	 */
	@Override
	public int numQubits() {
		return this.numQubits;
	}
}
